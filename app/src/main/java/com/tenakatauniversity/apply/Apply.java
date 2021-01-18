package com.tenakatauniversity.apply;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.tenakatauniversity.MainActivity;
import com.tenakatauniversity.R;
import com.tenakatauniversity.databinding.ApplyFragmentBinding;
import com.tenakatauniversity.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class Apply extends Fragment {

    private ApplyViewModel viewModel;
    private ApplyFragmentBinding binding;

    //launcher to handle camera permission request result
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    //take the picture
                    dispatchTakePictureIntent();
                } else {
                    //explain why the app needs the permission
                    showCameraPermissionRationale();
                }
            });

    //launcher to handle location permission request result
    private final ActivityResultLauncher<String[]> requestLocationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean status = true;
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    if (!entry.getValue())
                        status = false;
                }
                if (status) {
                    getLocation();
                } else {
                    Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    // Create this as a variable in your Fragment class
    ActivityResultLauncher<Intent> cameraIntentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //handle the result image
                Bundle extras = result.getData().getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    binding.pictureImageView.setImageBitmap(imageBitmap);
                }
            });
    private String currentPhotoPath;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ApplyFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ApplyViewModel.class);
        Utility.updateFragmentTitle(this, R.string.enter_application_details);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        requestLocationPermission();
//        getLastKnownLocation();
        viewModel.getValidateFieldsLiveData().observe(getViewLifecycleOwner(), validate -> {
            if (validate != null && validate) {
                this.validateFields();
                viewModel.resetValidateLiveData();
            }
        });

        viewModel.getTakePictureLiveData().observe(getViewLifecycleOwner(), takePicture -> {
            if (takePicture != null && takePicture) {
                requestCameraPermission();
            }
        });

        viewModel.locationMutableLiveData.observe(getViewLifecycleOwner(), location -> {
            if (location != null) {
                try {
                    Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                    //initialize address list
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    binding.countryValueTextView.setText(addressList.get(0).getCountryName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        return binding.getRoot();
    }

    private void getLocation() {
        Timber.d("get location");
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("permissions granted in get location");
            //check condition
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Timber.d("providers enabled");
                //when location service is enabled, get last known location
                fusedLocationClient.getLastLocation().addOnCompleteListener(requireActivity(), task -> {
                    //initialize location
                    Location location = task.getResult();
                    if (location != null) {
                        viewModel.locationMutableLiveData.setValue(location);
                    } else {
                        Timber.d("location is null");
                        //location is null, initialize location request
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(2000)
                                .setFastestInterval(2000)
                                .setNumUpdates(1);
                        //initialize location callback
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                //initialize location
                                Location location1 = locationResult.getLastLocation();
                                viewModel.locationMutableLiveData.setValue(location1);
                            }
                        };
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                });
            } else {
                //when location service is not enabled, open location setting
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else {
            requestLocationPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }


    /**
     * Validate the application input fields
     */
    public boolean validateFields() {

        //validate name
        String name = binding.nameTextInputLayout.getEditText().getText().toString().trim();
        if (name == null || name.isEmpty()) {
            binding.nameTextInputLayout.setError(getString(R.string.name_error));
            return false;
        } else {
            binding.nameTextInputLayout.setError("");
        }

        //validate age
        String ageString = binding.ageTextInputLayout.getEditText().getText().toString().trim();
        if (ageString == null || ageString.isEmpty()) {
            binding.ageTextInputLayout.setError(getString(R.string.age_error));
            return false;
        } else {
            binding.ageTextInputLayout.setError("");
        }

        try {
            int age = Integer.parseInt(ageString);
            if (age <= 0) {
                binding.ageTextInputLayout.setError(getString(R.string.age_invalid_error));
                return false;
            } else {
                binding.ageTextInputLayout.setError("");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.ageTextInputLayout.setError(getString(R.string.age_invalid_error));
            return false;
        }

        // validate gender
        if (!binding.maleRadioButton.isChecked() && !binding.femaleRadioButton.isChecked()) {
            Snackbar.make(binding.coordinatorLayout, R.string.gender_error, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        // validate marital status
        if (!binding.singleRadioButton.isChecked() && !binding.marriedRadioButton.isChecked()) {
            Snackbar.make(binding.coordinatorLayout, R.string.marital_status_error, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        //validate height
        String heightString = binding.heightTextInputLayout.getEditText().getText().toString().trim();
        if (heightString == null || heightString.isEmpty()) {
            binding.heightTextInputLayout.setError(getString(R.string.height_error));
            return false;
        } else {
            binding.heightTextInputLayout.setError("");
        }
        try {
            int height = Integer.parseInt(heightString);
            if (height <= 0) {
                binding.heightTextInputLayout.setError(getString(R.string.height_invalid_error));
                return false;
            } else {
                binding.heightTextInputLayout.setError("");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.heightTextInputLayout.setError(getString(R.string.height_invalid_error));
            return false;
        }

        //validate IQ test results
        String iqString = binding.iqTestResultsTextInputLayout.getEditText().getText().toString().trim();
        if (iqString == null || iqString.isEmpty()) {
            binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_error));
            return false;
        } else {
            binding.iqTestResultsTextInputLayout.setError("");
        }
        try {
            int iqTestResults = Integer.parseInt(iqString);
            if (iqTestResults <= 100) {
                binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_invalid_error));
                return false;
            } else {
                binding.iqTestResultsTextInputLayout.setError("");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_invalid_error));
            return false;
        }

        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Intent.createChooser(takePictureIntent, getString(R.string.capture_your_profile_picture));
//            startActivity(takePictureIntent);
            cameraIntentLauncher.launch(takePictureIntent);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    /*private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                // display error state to the user
                Snackbar.make(binding.coordinatorLayout, R.string.camera_error, Snackbar.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.tenakatauniversity.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //handle image capture result
                Bundle extras = takePictureIntent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                binding.pictureImageView.setImageBitmap(imageBitmap);
            }
        }
    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            dispatchTakePictureIntent();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            //explain to the user why the app needs the permission
            showCameraPermissionRationale();
        } else {
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void requestLocationPermission() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
                &&
                (ContextCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
            // You can use the API that requires the permission.
            getLocation();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //explain to the user why the app needs the permission
            showLocationPermissionRationale();
        } else {
            // The registered ActivityResultCallback gets the result of this request.
            requestLocationPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }

    private void showCameraPermissionRationale() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.camera_permission_request);
        builder.setMessage(R.string.camera_permission_rationale);
        builder.setPositiveButton(R.string.done, (dialogInterface, i) -> {
        });
        builder.show();
    }

    /**
     * Show permission rationale if the user denies the location permission
     */
    private void showLocationPermissionRationale() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.location_permission_request);
        builder.setMessage(R.string.location_permission_rationale);
        builder.setPositiveButton(R.string.done, (dialogInterface, i) -> {
        });
        builder.show();
    }

}