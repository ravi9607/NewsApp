package com.example.dailynews

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.select_activity.*
import java.lang.Math.abs

@Suppress("DEPRECATION")
class SportsActivity : AppCompatActivity(), NewsItemClicked , GestureDetector.OnGestureListener{


    lateinit var gestureDetector: GestureDetector              // for onTouchEvent
    var x1:Float=0.0f
    var x2:Float=0.0f
    var y1:Float=0.0f
    var y2:Float=0.0f


    private lateinit var mAdapter: newsAdapter
    var currentUrl: String? =null
    var isLike : Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_activity)

        supportActionBar?.title = "Sports News"

        gestureDetector = GestureDetector(this,this)

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
        progressBar.visibility = View.VISIBLE
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=9485dbed563145c5b58b6800baf4c4be"
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
        currentUrl= Uri.parse(item.url).toString()
    }

    fun shareNews(view: View){
//        val i = Intent(Intent.ACTION_SEND)
//        i.type = "text/plain"
//        i.putExtra(Intent.EXTRA_TEXT, "Hi, checkout this news  ${currentUrl}")
//        startActivity(Intent.createChooser(i, "Share this news with"))


        Toast.makeText(this,"SHAREING . ${currentUrl}", Toast.LENGTH_LONG).show()
    }

    override fun onShareClick(item: News) {
        currentUrl= Uri.parse(item.url).toString()


        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout Important News  ${currentUrl}")
        val chooser = Intent.createChooser(intent,"share News")
        startActivity(chooser)
    }

    override fun onlikeNews(item: News) {
        Toast.makeText(this, "Like clicked ", Toast.LENGTH_SHORT).show()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when(event?.action){

            0->{
                x1=event.x
                y1=event.y
            }
            1->{
                x2=event.x
                y2=event.y
                val valueX:Float=x2-x1
                val valueY:Float=y2-y1
                if(abs(valueX)>10){
                    if(x2>x1){
                        Toast.makeText(this,"Right Swap",Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this,"Left Swap",Toast.LENGTH_LONG).show()

                    }
                }else{
                    if(y2>y1){
                        //supportActionBar?.hide()
                        Toast.makeText(this,"Top Swap",Toast.LENGTH_LONG).show()
                    }else{
                        //supportActionBar?.isShowing
                        Toast.makeText(this,"Down Swap",Toast.LENGTH_LONG).show()
                    }

                }

        }

        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Toast.makeText(this,"onDown",Toast.LENGTH_LONG).show()
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        Toast.makeText(this,"onShowPress",Toast.LENGTH_LONG).show()
       // TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Toast.makeText(this,"onSingleTapup",Toast.LENGTH_LONG).show()
       // TODO("Not yet implemented")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
       // TODO("Not yet implemented")
        Toast.makeText(this,"onScroll",Toast.LENGTH_LONG).show()
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        Toast.makeText(this,"onLongPress",Toast.LENGTH_LONG).show()
       // TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //TODO("Not yet implemented")
        Toast.makeText(this,"onFling",Toast.LENGTH_LONG).show()
        return false
    }



//    fun likebtn(view: View) {
//        isLike=true
//        if(isLike == true){
//            like.setImageDrawable(ContextCompat.getDrawable(like.context,R.drawable.like1))
//        }
//    }

}