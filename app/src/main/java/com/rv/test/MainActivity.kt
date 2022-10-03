package com.rv.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.rv.test.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val rvList: List<RecyclerView> by lazy {
        binding.run {
            listOf(rv1, rv2, rv3, rv4, rv5, rv6)
        }
    }
    private val adapters: List<MyListAdapter<TestItem, *>> by lazy {
        (0..5).map {
            if (it == 0) {
                Adapter3()
            } else {
                Adapter2()
            }
        }
    }
    private val vm = MainViewModel()
    private val map: Map<RecyclerView, RecyclerView.OnScrollListener> by lazy {
        val m = mutableMapOf<RecyclerView, RecyclerView.OnScrollListener>()
        rvList.forEach {
            m[it] = createRvOnScrollListener()
        }
        m
    }

    val diffCallback = DiffCallback()

    private lateinit var differ: AsyncListDiffer<TestItem>
    private val sharedAdapter = Adapter2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val listUpdateCallback = MyListUpdateCallback(ArrayList(adapters))
        differ = AsyncListDiffer(listUpdateCallback, AsyncDifferConfig.Builder(diffCallback).build())
        adapters.forEach {
            it.setDiffer(differ)
        }

//        val listUpdateCallback = AdapterListUpdateCallback(sharedAdapter)
//        differ = AsyncListDiffer(listUpdateCallback, AdapterListUpdateCallback.Builder(diffCallback).build())
//        sharedAdapter.setDiffer(differ)

        with(binding) {
            rvList.forEachIndexed { i, it ->
                it.layoutManager = LinearLayoutManager(this@MainActivity)
                it.itemAnimator = null
                it.adapter = adapters[i]
                it.addOnScrollListener(map[it]!!)
                it.recycledViewPool.setMaxRecycledViews(0, 10)
            }
            tvAdd.setOnClickListener {
                vm.addItem()
            }
            tvTest.setOnClickListener {
                tvAdd.performClick()
                rv6.fling(0, 50000)
            }
            tvStart.setOnClickListener {
                vm.startMonkeyTest()
            }
            tvStop.setOnClickListener {
                vm.stopMonkeyTest()
            }
            tvReset.setOnClickListener {
                finish()
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
            }

            vm.itmEvent.observe(this@MainActivity) {
                val sign = if (Random.nextInt(1, 3) % 2 == 0) 1 else -1
                val speed = sign * 50000
                Log.e("random", speed.toString())
//                rv6.fling(0, speed)
            }
            vm.data.observe(this@MainActivity) { data ->
//                rvList.forEach {
//                    (it.adapter as Adapter).submitList(data.toList())
//                }
                (rv6.adapter as MyListAdapter<TestItem, *>).submitList(data.toList())
            }
        }
    }

    private fun createRvOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val others = map.entries.filter { recyclerView != it.key }
                others.forEach { it.key.removeOnScrollListener(it.value) }
                others.forEach { it.key.scrollBy(0, dy) }
                others.forEach { it.key.addOnScrollListener(it.value) }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
//                val state = when (newState) {
//                    1 -> "dragging"
//                    2 -> "settling"
//                    else -> " idle"
//                }
//                Log.e("scrollstate", "${recyclerView.hashCode()}, $state")
            }
        }
    }
}