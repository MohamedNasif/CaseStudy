package droidefense.handler;

import droidefense.handler.base.AbstractHandler;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;
import droidefense.ml.MLResultHolder;
import droidefense.ml.WekaClassifier;
import droidefense.sdk.OutPutResult;
import droidefense.sdk.manifest.UsesPermission;
import droidefense.sdk.util.InternalConstant;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by sergio on 6/3/16.
 */
public class WekaResultsHandler extends AbstractHandler {

    private static final int ATRRIBUTE_NUMBER = 206;
    private static final String[] attributesList = load();
    private File featuresFile;
    private WekaClassifier classifier;

    private static String[] load() {
        HashSet<String> names = new HashSet<>();
        names.add("ACCESS_ALL_DOWNLOADS");
        names.add("ACCESS_COARSE_LOCATION");
        names.add("ACCESS_DOWNLOAD_MANAGER_ADVANCED");
        names.add("ACCESS_DRM");
        names.add("ACCESS_FINE_LOCATION");
        names.add("ACCESS_KEYGUARD_SECURE_STORAGE");
        names.add("ACCESS_LOCATION_EXTRA_COMMANDS");
        names.add("ACCESS_MOCK_LOCATION");
        names.add("ACCESS_NETWORK_STATE");
        names.add("ACCESS_NOTIFICATIONS");
        names.add("ACCESS_WIFI_STATE");
        names.add("AUTHENTICATE_ACCOUNTS");
        names.add("BACKUP");
        names.add("BATTERY_STATS");
        names.add("BIND_DEVICE_ADMIN");
        names.add("BLUETOOTH");
        names.add("BLUETOOTH_ADMIN");
        names.add("BROADCAST_NETWORK_PRIVILEGED");
        names.add("BROADCAST_SCORE_NETWORKS");
        names.add("CALL_PRIVILEGED");
        names.add("CAPTURE_AUDIO_OUTPUT");
        names.add("CAPTURE_SECURE_VIDEO_OUTPUT");
        names.add("CAPTURE_VIDEO_OUTPUT");
        names.add("CHANGE_COMPONENT_ENABLED_STATE");
        names.add("CHANGE_NETWORK_STATE");
        names.add("CHANGE_WIFI_MULTICAST_STATE");
        names.add("CHANGE_WIFI_STATE");
        names.add("CLEAR_APP_CACHE");
        names.add("CLEAR_APP_USER_DATA");
        names.add("CONFIGURE_WIFI_DISPLAY");
        names.add("CONNECTIVITY_INTERNAL");
        names.add("CONTROL_VPN");
        names.add("DELETE_CACHE_FILES");
        names.add("DELETE_PACKAGES");
        names.add("DEVICE_POWER");
        names.add("DISABLE_KEYGUARD");
        names.add("DOWNLOAD_CACHE_NON_PURGEABLE");
        names.add("DUMP");
        names.add("EXPAND_STATUS_BAR");
        names.add("FILTER_EVENTS");
        names.add("FRAME_STATS");
        names.add("FREEZE_SCREEN");
        names.add("GET_ACCOUNTS");
        names.add("GET_APP_OPS_STATS");
        names.add("GET_PACKAGE_SIZE");
        names.add("GLOBAL_SEARCH");
        names.add("GRANT_REVOKE_PERMISSIONS");
        names.add("INSTALL_DRM");
        names.add("INSTALL_LOCATION_PROVIDER");
        names.add("INSTALL_PACKAGES");
        names.add("INTERACT_ACROSS_USERS");
        names.add("INTERACT_ACROSS_USERS_FULL");
        names.add("INTERNAL_SYSTEM_WINDOW");
        names.add("INTERNET");
        names.add("LOCATION_HARDWARE");
        names.add("MAGNIFY_DISPLAY");
        names.add("MANAGE_ACCOUNTS");
        names.add("MANAGE_APP_TOKENS");
        names.add("MANAGE_CA_CERTIFICATES");
        names.add("MANAGE_DEVICE_ADMINS");
        names.add("MANAGE_DOCUMENTS");
        names.add("MANAGE_NETWORK_POLICY");
        names.add("MANAGE_USB");
        names.add("MANAGE_USERS");
        names.add("MARK_NETWORK_SOCKET");
        names.add("MEDIA_CONTENT_CONTROL");
        names.add("MODIFY_AUDIO_ROUTING");
        names.add("MODIFY_AUDIO_SETTINGS");
        names.add("MODIFY_NETWORK_ACCOUNTING");
        names.add("MODIFY_PHONE_STATE");
        names.add("MOVE_PACKAGE");
        names.add("NFC");
        names.add("PACKAGE_USAGE_STATS");
        names.add("PACKAGE_VERIFICATION_AGENT");
        names.add("READ_CONTACTS");
        names.add("READ_DREAM_STATE");
        names.add("READ_FRAME_BUFFER");
        names.add("READ_LOGS");
        names.add("READ_NETWORK_USAGE_HISTORY");
        names.add("READ_PHONE_STATE");
        names.add("READ_PRIVILEGED_PHONE_STATE");
        names.add("READ_PROFILE");
        names.add("READ_SOCIAL_STREAM");
        names.add("READ_SYNC_SETTINGS");
        names.add("READ_SYNC_STATS");
        names.add("REBOOT");
        names.add("RECEIVE_SMS");
        names.add("REMOTE_AUDIO_PLAYBACK");
        names.add("RETRIEVE_WINDOW_INFO");
        names.add("SCORE_NETWORKS");
        names.add("SEND_SMS");
        names.add("SERIAL_PORT");
        names.add("SET_ANIMATION_SCALE");
        names.add("SET_INPUT_CALIBRATION");
        names.add("SET_KEYBOARD_LAYOUT");
        names.add("SET_ORIENTATION");
        names.add("SET_POINTER_SPEED");
        names.add("SET_PREFERRED_APPLICATIONS");
        names.add("SET_WALLPAPER");
        names.add("SET_WALLPAPER_COMPONENT");
        names.add("SET_WALLPAPER_HINTS");
        names.add("SHUTDOWN");
        names.add("STATUS_BAR");
        names.add("STATUS_BAR_SERVICE");
        names.add("TRANSMIT_IR");
        names.add("UPDATE_APP_OPS_STATS");
        names.add("UPDATE_DEVICE_STATS");
        names.add("UPDATE_LOCK");
        names.add("USE_CREDENTIALS");
        names.add("USE_SIP");
        names.add("VIBRATE");
        names.add("WAKE_LOCK");
        names.add("WRITE_APN_SETTINGS");
        names.add("WRITE_CONTACTS");
        names.add("WRITE_DREAM_STATE");
        names.add("WRITE_PROFILE");
        names.add("WRITE_SECURE_SETTINGS");
        names.add("WRITE_SETTINGS");
        names.add("WRITE_SMS");
        names.add("WRITE_SOCIAL_STREAM");
        names.add("WRITE_SYNC_SETTINGS");
        names.add("com.android.email.permission.ACCESS_PROVIDER");
        names.add("com.android.printspooler.permission.ACCESS_ALL_PRINT_JOBS");
        names.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        names.add("com.android.voicemail.permission.READ_WRITE_ALL_VOICEMAIL");
        names.add("ACCESS_CHECKIN_PROPERTIES");
        names.add("ACCESS_NOTIFICATION_POLICY");
        names.add("ACCOUNT_MANAGER");
        names.add("ADD_VOICEMAIL");
        names.add("BIND_ACCESSIBILITY_SERVICE");
        names.add("BIND_APPWIDGET");
        names.add("BIND_CARRIER_MESSAGING_SERVICE");
        names.add("BIND_CARRIER_SERVICES");
        names.add("BIND_CHOOSER_TARGET_SERVICE");
        names.add("BIND_DREAM_SERVICE");
        names.add("BIND_INCALL_SERVICE");
        names.add("BIND_INPUT_METHOD");
        names.add("BIND_MIDI_DEVICE_SERVICE");
        names.add("BIND_NFC_SERVICE");
        names.add("BIND_NOTIFICATION_LISTENER_SERVICE");
        names.add("BIND_PRINT_SERVICE");
        names.add("BIND_REMOTEVIEWS");
        names.add("BIND_TELECOM_CONNECTION_SERVICE");
        names.add("BIND_TEXT_SERVICE");
        names.add("BIND_TV_INPUT");
        names.add("BIND_VOICE_INTERACTION");
        names.add("BIND_VPN_SERVICE");
        names.add("BIND_WALLPAPER");
        names.add("BLUETOOTH_PRIVILEGED");
        names.add("BODY_SENSORS");
        names.add("BROADCAST_PACKAGE_REMOVED");
        names.add("BROADCAST_SMS");
        names.add("BROADCAST_STICKY");
        names.add("BROADCAST_WAP_PUSH");
        names.add("CALL_PHONE");
        names.add("CAMERA");
        names.add("CHANGE_CONFIGURATION");
        names.add("CONTROL_LOCATION_UPDATES");
        names.add("DIAGNOSTIC");
        names.add("FACTORY_TEST");
        names.add("FLASHLIGHT");
        names.add("GET_ACCOUNTS_PRIVILEGED");
        names.add("GET_TASKS");
        names.add("INSTALL_SHORTCUT");
        names.add("KILL_BACKGROUND_PROCESSES");
        names.add("MASTER_CLEAR");
        names.add("MOUNT_FORMAT_FILESYSTEMS");
        names.add("MOUNT_UNMOUNT_FILESYSTEMS");
        names.add("PERSISTENT_ACTIVITY");
        names.add("PROCESS_OUTGOING_CALLS");
        names.add("READ_CALENDAR");
        names.add("READ_CALL_LOG");
        names.add("READ_EXTERNAL_STORAGE");
        names.add("READ_INPUT_STATE");
        names.add("READ_SMS");
        names.add("READ_VOICEMAIL");
        names.add("RECEIVE_BOOT_COMPLETED");
        names.add("RECEIVE_MMS");
        names.add("RECEIVE_WAP_PUSH");
        names.add("RECORD_AUDIO");
        names.add("REORDER_TASKS");
        names.add("REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
        names.add("REQUEST_INSTALL_PACKAGES");
        names.add("RESTART_PACKAGES");
        names.add("SEND_RESPOND_VIA_MESSAGE");
        names.add("SET_ALARM");
        names.add("SET_ALWAYS_FINISH");
        names.add("SET_DEBUG_APP");
        names.add("SET_PROCESS_LIMIT");
        names.add("SET_TIME");
        names.add("SET_TIME_ZONE");
        names.add("SIGNAL_PERSISTENT_PROCESSES");
        names.add("SYSTEM_ALERT_WINDOW");
        names.add("UNINSTALL_SHORTCUT");
        names.add("USE_FINGERPRINT");
        names.add("WRITE_CALENDAR");
        names.add("WRITE_CALL_LOG");
        names.add("WRITE_EXTERNAL_STORAGE");
        names.add("WRITE_GSERVICES");
        names.add("WRITE_VOICEMAIL");
        names.add("READ_WRITE_ALL_VOICEMAIL");
        //add my extra permissions
        names.add("harmlessPermission");
        names.add("canStealDataPermissions");
        names.add("communication");
        names.add("dangerours");
        names.add("dangerousSpecial");
        return names.toArray(new String[names.size()]);
    }

    @Override
    public boolean doTheJob() {
        classifier = new WekaClassifier();
        try {
            featuresFile = new File(FileIOHandler.getProjectFolderPath(project) + File.separator + InternalConstant.WEKA_FEATURES_FILE);
            //generate features as weka file format
            generateFeatures();
            //evaluate them with models
            evaluate();
            return true;
        } catch (Exception e) {
            Log.write(LoggerType.FATAL, "Exception while evaluating Machine Learning models", e.getLocalizedMessage());
            return false;
        }
    }

    private void generateFeatures() {
        //old way, but functional

        //get data
        ArrayList<UsesPermission> permList = project.getManifestInfo().getUsesPermissionList();
        OutPutResult result = new OutPutResult(permList);
        //get result
        String data = result.toWekaData();
        //save
        //convert data to weka format
        String[] names = OutPutResult.getGlobalPermissionList();
        String header = "@relation relation\r\n";
        header += "\r\n";
        for (String attr : names) {
            header += "@attribute " + attr + " {false, true}\r\n";
        }
        header += "\r\n";
        header += "@attribute class {MALWARE, GOODWARE} \r\n";
        header += "\r\n";
        header += "@data\r\n";
        header += "\r\n";
        String body = data + ",?\r\n";

        String out = header + body;
        FileIOHandler.saveFile(featuresFile, out);
    }

    private void evaluate() throws Exception {

        Log.write(LoggerType.TRACE, "Starting WEKA classifier...");

        File modelDir = FileIOHandler.getModelsDir();

        if (featuresFile == null || !featuresFile.exists()) {
            throw new IOException("Sample feature file was not found");
        }
        if (!modelDir.exists()) {
            throw new IOException("Machine Learning model dir not found");
        }

        //classifier names
        File[] modelFiles = modelDir.listFiles();

        if (modelFiles == null || modelFiles.length == 0) {
            throw new IOException("There are no machine learning models available on specified folder");
        }


        /*
         * First we load the training data from our ARFF file
         */
        ArffLoader trainLoader = new ArffLoader();

        trainLoader.setSource(featuresFile);
        Instances unlabeled = trainLoader.getDataSet();

        /*
         * Now we tell the data set which attribute we want to classify, in our
         * case, we want to classify the first column: survived
         */
        unlabeled.setClassIndex(unlabeled.size() - 1);

        // Use a set of classifiers
        Classifier[] models;

        /*
         * Now we read in the serialized apimodel from disk
         */
        models = classifier.readModels(modelFiles);

        MLResultHolder result = new MLResultHolder();

        // Run for each classifier
        for (int j = 0; j < models.length; j++) {
            /*
            Evaluation eval = new Evaluation(unlabeled);
            eval.evaluateModel(models[j], unlabeled);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            result.add(modelFiles[j].getName().replace(".apimodel", ""), eval.correct());
            */
            double clsLabel = models[j].classifyInstance(unlabeled.instance(0));
            String tag = modelFiles[j].getName().replace(".model", "");
            result.add(tag, clsLabel);
        }
        result.updateMalwareRatio();
        project.setMachineLearningResult(result);
        Log.write(LoggerType.TRACE, "WEKA classification done!");
    }
}
