package com.peopleHere.people_here.AddPicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.R
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.AddPicture.PictureDB.PictureEntity
import com.peopleHere.people_here.Data.AddPicturLocationData
import com.peopleHere.people_here.TitleCategory.TitleActivity
import com.peopleHere.people_here.databinding.ActivityAddPictureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPictureBinding
    private var addPictureAdapter: AddPictureAdapter? = null
    private var addLocationPictureAdapter: AddPictureLocationAdapter? = null

    var pictureDB: PictureDB? = null//없으면 null 로
    var uriString: String? = null
    val picturelist = arrayListOf<PictureEntity>()
    var addLocationlist = ArrayList<AddPicturLocationData>()
    var itemsize = 0
    var nextButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPictureBinding.inflate(layoutInflater)

        addLocationPictureAdapter = AddPictureLocationAdapter(addLocationlist)
        binding.rvLocation.adapter = addLocationPictureAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setupItemTouchHelper()

        val resourceUri_1 = Uri.parse("android.resource://$packageName/${R.drawable.photoaddition}")
        val resourceUri_2 =
            Uri.parse("android.resource://$packageName/${R.drawable.photoaddition2}")
        val resourceUri_full =
            Uri.parse("android.resource://$packageName/${R.drawable.photoaddition3}")


        pictureDB = PictureDB.getInstance(this) //인스턴스 생성


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val receivedIntent = intent
                if (receivedIntent != null) {
                    // ArrayList 크기만큼 반복
                    //TODO:여기가 CustomAlbum에서 받아와 지는 경우를 말한다.
                    var i = 0

                    while (receivedIntent.hasExtra("uri_$i")) {
                        uriString = receivedIntent.getStringExtra("uri_$i").toString()
                        Log.d("test1", uriString!!)
                        pictureDB!!.getPictureDao()
                            .addPicture(PictureEntity(uriString!!, "멘쇼 츠케멘", "jungan", 1, i))
                        i++
                    }
                    pictureDB!!.getPictureDao().deletePicture(resourceUri_1.toString())
                    pictureDB!!.getPictureDao().deletePicture(resourceUri_2.toString())
                    pictureDB!!.getPictureDao()
                        .addPicture(
                            PictureEntity(
                                resourceUri_2.toString(),
                                "멘쇼 츠케멘",
                                "jungan",
                                0,
                                i
                            )
                        )

                    //얘가 마지막 위치에 있어야 해서 그냥 지우고 계속 추가 하는중
                }
                //TODO:나중에 코스도 엮어서
                var products = pictureDB!!.getPictureDao().getPicture()//products로 product 가져옴
                Log.d("sizeCheck", products.size.toString())
                if (products.size < 2) {//TODO:왜 비었다 뜨죵??
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)
                    nextButton = false
                } else {
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                    nextButton = true
                }

                if (products.isEmpty()) {//앞에서 이미 회색 추가 해서 어림도 없다... 이 부분을 해결 어케하지??
                    pictureDB!!.getPictureDao()
                        .addPicture(
                            PictureEntity(
                                resourceUri_1.toString(),
                                "멘쇼 츠케멘",
                                "jungan",
                                0,
                                0
                            )
                        )

                }
                (binding.rvPictures.adapter as AddPictureAdapter).setData(products)//datasetting 선언 했으니 가져가야함 아마 엮어주는 역할?

            }

        }
        attachPictureAdapter()

        //얘는 RV notify해줘야 리사이클러뷰가 적용해서 할 듯 !!
        lifecycleScope.launch {
            // 백그라운드에서 실행되어야 하는 코드
            var pictures = withContext(Dispatchers.IO) {
                pictureDB!!.getPictureDao().getPicture()
            }
        }


        setContentView(binding.root)
    }


    private fun attachPictureAdapter() {
        addPictureAdapter = AddPictureAdapter(picturelist, this)



        binding.rvPictures.adapter = addPictureAdapter
        binding.rvPictures.layoutManager =
            GridLayoutManager(this, 2)
        addPictureAdapter!!.setOnItemClickListener(object :
            AddPictureAdapter.OnItemClickListener {
            override fun onItemClick(size: Int) {
                //개수 어케 받지 흠
                if (size < 2) {//TODO:왜 비었다 뜨죵??
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)
                    nextButton = false
                } else {
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                    nextButton = true
                }
                //여기서 개수 받아와서 해야할듯??
            }
        })

        binding.btnNext.setOnClickListener {
            if (nextButton) {//TODO:왜 비었다 뜨죵??
                val intent = Intent(this, TitleActivity::class.java)
                startActivity(intent)
            }
            //finish()
        }

        addPictureAdapter!!.notifyItemInserted(picturelist.size)

        binding.btnAddPicture.setOnClickListener {
            val bottomsheet = LocationChooseFragment()
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
            val intent = Intent(this, CustomAlbumActivity::class.java)
            intent.putExtra("key", "value")
        }

        setContentView(binding.root)
    }


    private fun setupItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            //항상 드래그 가능
            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            //상하좌우
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }

            //포지션 바뀌게
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                if (toPosition == picturelist.size - 1) {
                    return false
                }
                addPictureAdapter?.onItemMove(fromPosition, toPosition)
                return true

            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 추후 swipe 기능 추가 되면 추가
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvPictures)
    }
}