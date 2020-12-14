package com.example.dailynews

import android.app.PendingIntent.getActivity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: newsAdapter
    var currentUrl: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = newsAdapter(this)
        recyclerView.adapter = mAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.refresh ->{
                fetchData()

                true
            }

            else -> return super.onOptionsItemSelected(item)
        }


    }

    private fun fetchData(){
        progressBar.visibility = View.VISIBLE
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=0a0230f4833a48a59dbfefdc4bc7d3ef"
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {

                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("description")
                    )
                    newsArray.add(news)
                    progressBar.visibility = View.GONE
                    //Toast.makeText(this," updated", Toast.LENGTH_LONG).show()
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

        //Toast.makeText(this,"CLICKED ..${Uri.parse(item.url)}",Toast.LENGTH_LONG).show()
        currentUrl= Uri.parse(item.url).toString()
    }

    fun shareNews(view: View){
//        val i = Intent(Intent.ACTION_SEND)
//        i.type = "text/plain"
//        i.putExtra(Intent.EXTRA_TEXT, "Hi, checkout this news  ${currentUrl}")
//        startActivity(Intent.createChooser(i, "Share this news with"))


        Toast.makeText(this,"SHAREING . ${currentUrl}", Toast.LENGTH_LONG).show()
    }

}