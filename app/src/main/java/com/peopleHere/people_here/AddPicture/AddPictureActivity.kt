package com.peopleHere.people_here.AddPicture

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.i
import com.peopleHere.people_here.R
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.AddPicture.PictureDB.PictureEntity
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.Data.AddPicturLocationData

import com.peopleHere.people_here.Data.AddPictureData
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.MainActivity

import com.peopleHere.people_here.MakingTour.AddListDataManager
import com.peopleHere.people_here.TitleCategory.TitleActivity
import com.peopleHere.people_here.databinding.ActivityAddPictureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File


class AddPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPictureBinding
    private var addPictureAdapter: AddPictureAdapter? = null
    private var addLocationPictureAdapter: AddPictureLocationAdapter? = null

    var pictureDB: PictureDB? = null//없으면 null 로
    var uriString: String? = null
    val picturelist = arrayListOf<PictureEntity>()
    var addLocationlist: ArrayList<AddPicturLocationData> = arrayListOf()
    var itemsize = 0
    var nextButton: Boolean = false
    var sendPictureNum = 0
    var time = 0

    private val TAG = this.javaClass.simpleName

    //콜백 인스턴스 생성
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    pictureDB!!.getPictureDao().deleteAllPictures()
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddPictureBinding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    pictureDB!!.getPictureDao().deleteAllPictures()

                    finish()
                }
            }

        }
        this.onBackPressedDispatcher.addCallback(this, callback)


        for (i in 0 until AddListDataManager.addListData.size) {
            addLocationlist.add(
                AddPicturLocationData(
                    AddListDataManager.addListData[i].placeName,
                    AddListDataManager.addListData[i].placeImage, 0
                )
            )

           //장소 이름 끝

        }
        var getlocation = intent?.getStringExtra("location")
        if (getlocation == null) {
            getlocation = "None"
        }

        time = intent.getIntExtra("time", 0)

        addLocationPictureAdapter = AddPictureLocationAdapter(addLocationlist, this, getlocation)
        binding.rvLocation.adapter = addLocationPictureAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setupItemTouchHelper()

        val resourceUri_1 = Uri.parse("android.resource://$packageName/${R.drawable.photoaddition}")
        val resourceUri_2 =
            Uri.parse("android.resource://$packageName/${R.drawable.photoaddition2}")
        val resourceUri_full =
            Uri.parse("android.resource://$packageName/${R.drawable.photoaddition3}")


        pictureDB = PictureDB.getInstance(this, false) //인스턴스 생성


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {

                val receivedIntent = intent
                if (receivedIntent != null) {
                    // ArrayList 크기만큼 반복
                    //TODO:여기가 CustomAlbum에서 받아와 지는 경우를 말한다.
                    var i = 0
                    var getlocation = intent.getStringExtra("location")
                    //TODO:임의의 추가 부분


                    while (receivedIntent.hasExtra("uri_$i")) {
                        //TODO:애네도 어플리 케이션에 넣어야 함 픽쳐 ㅜㅊ가가

                        uriString = receivedIntent.getStringExtra("uri_$i").toString()
                        Log.d("test1", uriString!!)
                        pictureDB!!.getPictureDao()
                            .addPicture(PictureEntity(uriString!!, getlocation!!, "jungan", 1, i))

                        ApplicationClass.pencodingString?.add("Y29udGVudDovL21lZGlhL2V4dGVybmFsL2ltYWdlcy9tZWRpYS8xMDAwMDAyNzE5")
                        ApplicationClass.poriginalFileName?.add("abcd.jpg")
                        //TODO :얘네 인코딩 안되서 그냥 쓰레기 데이터 넣음
                        i++
                    }
                    //몇 번째꺼지??locationlist중?? 이거는어떻게 처리할까??

                    if (getlocation != null) {
                        pictureDB!!.getPictureDao().getPictureNum(getlocation!!)
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
                /*          if (products.size < 2) {//TODO:사이즈 말고 각각 로케이션이 1보다 클때로 해야할듯??
                              binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)
                              nextButton = false
                          } else {
                              binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                              nextButton = true
                          }*/


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



        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 0 until addLocationlist.size) {
                    //만약 여기서 비교하는데 pict
                    var count =
                        pictureDB!!.getPictureDao()
                            .getPictureNum(addLocationlist[i].location)//개수 가져와서 넣기
                    if (count < 1) {
                        //TODO:사이즈 말고 각각 로케이션이 1보다 클때로 해야할듯??
                        binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)
                        nextButton = false
                        break
                    } else {
                        binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                        nextButton = true
                    }
                }
            }
        }

        binding.ivCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, TitleActivity::class.java)
            intent.putExtra("time",time)
            startActivity(intent)

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
                //여기는 정말 잘 돌아가는데 다른 부분이 문제네용
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        for (i in 0 until addLocationlist.size) {
                            //만약 여기서 비교하는데 pict

                            var count =
                                pictureDB!!.getPictureDao()
                                    .getPictureNum(addLocationlist[i].location)//개수 가져와서 넣기

                            if (count < 1) {
                                //TODO:사이즈 말고 각각 로케이션이 1보다 클때로 해야할듯??
                                binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)
                                nextButton = false
                                break
                            } else {
                                binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                                nextButton = true
                            }

                        }
                    }
                }
                //여기서 개수 받아와서 해야할듯??
            }
        })
        var getlocation = intent?.getStringExtra("location")
        if (getlocation == null) {
            getlocation = "None"
        }

        addLocationPictureAdapter = AddPictureLocationAdapter(addLocationlist, this, getlocation)
        binding.rvLocation.adapter = addLocationPictureAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        addLocationPictureAdapter!!.setOnItemClickListener(object :
            AddPictureLocationAdapter.OnItemClickListener {
            override fun onPictureNum(size: Int) {
                //해서 다음 fragment에 넘기기
                sendPictureNum = size
            }

        })



        binding.btnNext.setOnClickListener {
            //TODO:고치기

            if (nextButton) {//TODO:왜 비었다 뜨죵??

                val intent = Intent(this, TitleActivity::class.java)
                intent.putExtra("time", time)
                startActivity(intent)
            }
            //finish()
        }


        addPictureAdapter!!.notifyItemInserted(picturelist.size)

        binding.btnAddPicture.setOnClickListener {
            val bottomsheet = LocationChooseFragment()//여기서 데이터 넘겨야 할듯???
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