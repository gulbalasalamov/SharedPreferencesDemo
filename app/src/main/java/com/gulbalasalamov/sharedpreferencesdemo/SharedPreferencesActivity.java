package com.gulbalasalamov.sharedpreferencesdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class SharedPreferencesActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(android.R.id.content, new userPrefsFragment());
        transaction.commit();
    }

    public static class userPrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.userprefs);
            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();

            EditTextPreference etp = (EditTextPreference) findPreference("username");
            etp.setSummary("Kullanıcı adınız: " + sp.getString("username", "kullanıcı no1"));

            etp = (EditTextPreference) findPreference("mail");
            etp.setSummary("Maid adresiniz: " + sp.getString("mail", "eposta@eposta.com"));
        }

        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                              String key) {
            Preference pref = findPreference(key);
            if (pref instanceof EditTextPreference) {
                EditTextPreference etp = (EditTextPreference) pref;
                pref.setSummary(etp.getText());
            }
        }
    }
}
