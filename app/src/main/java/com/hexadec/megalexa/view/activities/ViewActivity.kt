package com.hexadec.megalexa.view.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.hexadec.megalexa.R
import com.hexadec.megalexa.model.Workflow

import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Lifecycle event handler, called when the activity is resumed
     */
    override fun onResume() {
        super.onResume()
        loadContent()
    }

    /**
     * Lifecycle event handler, called when the activity is paused
     */
    override fun onPause() {
        super.onPause()
        saveContent()
    }

    private fun loadContent() {

    }

    private fun saveContent() {

    }

}
