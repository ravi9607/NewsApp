package com.example.dailynews

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

//    private lateinit var mAdapter: newsAdapter
//    var currentUrl: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_item,menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.refresh ->{
//                true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    fun headlineExecute(view: View) {
        Toast.makeText(this, "Headline Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HeadlineActivity::class.java)
        startActivity(intent)
    }

    fun businessExecute(view: View) {
        Toast.makeText(this, "Business Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, BusinessActivity::class.java)
        startActivity(intent)
    }

    fun technologyExecute(view: View) {
        Toast.makeText(this, "Technology Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, TechnologyActivity::class.java)
        startActivity(intent)
    }

    fun scienceExecute(view: View) {
        Toast.makeText(this, "Science Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ScienceActivity::class.java)
        startActivity(intent)
    }

    fun sportsExecute(view: View) {
        Toast.makeText(this, "Sports Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, SportsActivity::class.java)
        startActivity(intent)
    }

    fun healthExecute(view: View) {
        Toast.makeText(this, "Health Section", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HealthActivity::class.java)
        startActivity(intent)
    }
}