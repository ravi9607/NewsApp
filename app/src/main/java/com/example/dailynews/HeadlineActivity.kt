package com.example.dailynews

import android.app.ProgressDialog
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
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.select_activity.*

@Suppress("DEPRECATION")
class HeadlineActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: newsAdapter
    var currentUrl: String? =null
    var isLike : Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_activity)

        supportActionBar?.title = "Headlines"

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
            R.id.type1 ->{
                Toast.makeText(this,"type1", Toast.LENGTH_LONG).show()
                item.setChecked(true)
                true
            }R.id.type2 ->{
                Toast.makeText(this,"type2", Toast.LENGTH_LONG).show()
                item.setChecked(true)
                true
            }R.id.type3 ->{
                Toast.makeText(this,"type3", Toast.LENGTH_LONG).show()
                item.setChecked(true)
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }


    }

    private fun fetchData(){
        //progressBar.visibility = View.VISIBLE

        //progressBar.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        val progressDialog = ProgressDialog(this@HeadlineActivity)
        progressDialog.setTitle("Fetching News ...")
        //progressDialog.setMessage("Fetching Latest News")
        progressDialog.show()

        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=9485dbed563145c5b58b6800baf4c4be"
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
                                newsJsonObject.getString("publishedAt"),
                                newsJsonObject.getString("description")
                        )
                        newsArray.add(news)
                        //progressBar.visibility = View.GONE
                        progressDialog.dismiss()
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

    override fun onShareClick(item: News) {
        currentUrl= Uri.parse(item.url).toString()


        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout Important News  ${currentUrl}")
        val chooser = Intent.createChooser(intent,"share News")
        startActivity(chooser)
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

    override fun onlikeNews(item: News) {
        Toast.makeText(this, "Like clicked ", Toast.LENGTH_SHORT).show()
    }

//    fun likebtn(view: View) {
//        isLike=true
//        if(isLike == true){
//            like.setImageDrawable(ContextCompat.getDrawable(like.context,R.drawable.like1))
//        }
//    }

}