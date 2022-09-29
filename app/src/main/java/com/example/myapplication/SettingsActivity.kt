package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity)

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        val autoResetSwitch = findViewById<Switch>(R.id.auto_reset_switch)
        val editor = sharedPreferences.edit()

        autoResetSwitch.isChecked = sharedPreferences.getBoolean(getString(R.string.AR), true)

        autoResetSwitch.setOnCheckedChangeListener {buttonView, isChecked ->
            if(isChecked)
                editor.putBoolean(getString(R.string.AR), true)
            else
                editor.putBoolean(getString(R.string.AR), false)
            editor.apply()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        else -> return super.onOptionsItemSelected(item)
        }
    }
}