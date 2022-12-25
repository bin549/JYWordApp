package com.android.jywordapp.activities

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.jywordapp.Constants
import com.android.jywordapp.Dao.WordDao
import com.android.jywordapp.R
import com.android.jywordapp.WordApp
import com.android.jywordapp.adapters.ItemAdapter
import com.android.jywordapp.databinding.ActivityWordEditorBinding
import com.android.jywordapp.databinding.DialogAddBinding
import com.android.jywordapp.databinding.DialogUpdateBinding
import com.android.jywordapp.model.WordEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class WordEditor : AppCompatActivity() {
    private var binding: ActivityWordEditorBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val userId = intent.getIntExtra(Constants.USER_ID, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_editor)
        binding = ActivityWordEditorBinding.inflate(layoutInflater)
        setSupportActionBar(binding?.toolbarWordEditorActivity)
        setContentView(binding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "个人空间"
        binding?.toolbarWordEditorActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val wordDao = (application as WordApp).db.wordDao()
        binding?.btnAdd?.setOnClickListener {
            addRecordDialog(wordDao, userId)
        }
        val search_view: SearchView = findViewById(R.id.search_view)
        lifecycleScope.launch {
            wordDao.fetchWordsByIsKnown(userId, 0).collect() {
                Log.d("word", "$it")
                val list = ArrayList(it)
                setupListOfDataIntoRecyclerView(list, wordDao, userId)
                search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        var tempArr = ArrayList<WordEntity>()
                        for (arr in list) {
                            if (arr.name!!.toLowerCase(Locale.getDefault()).contains(p0.toString())
                            ) {
                                tempArr.add(arr)
                            }
                        }
                        setupListOfDataIntoRecyclerView(tempArr, wordDao, userId)
                        return true
                    }
                })
            }
        }
        binding?.rbAllWords?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbKnownWords) {
                lifecycleScope.launch {
                    wordDao.fetchWordsByIsKnown(userId, 0).collect() {
                        Log.d("word", "$it")
                        val list = ArrayList(it)
                        setupListOfDataIntoRecyclerView(list, wordDao, userId)
                        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(p0: String?): Boolean {
                                return true
                            }

                            override fun onQueryTextChange(p0: String?): Boolean {
                                var tempArr = ArrayList<WordEntity>()
                                for (arr in list) {
                                    if (arr.name!!.toLowerCase(Locale.getDefault())
                                            .contains(p0.toString())
                                    ) {
                                        tempArr.add(arr)
                                    }
                                }
                                setupListOfDataIntoRecyclerView(tempArr, wordDao, userId)
                                return true
                            }
                        })
                    }
                }
            } else {
                lifecycleScope.launch {
                    wordDao.fetchWordsByIsKnown(userId, 1).collect() {
                        Log.d("word", "$it")
                        val list = ArrayList(it)
                        setupListOfDataIntoRecyclerView(list, wordDao, userId)
                        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(p0: String?): Boolean {
                                return true
                            }

                            override fun onQueryTextChange(p0: String?): Boolean {
                                var tempArr = ArrayList<WordEntity>()
                                for (arr in list) {
                                    if (arr.name!!.toLowerCase(Locale.getDefault())
                                            .contains(p0.toString())
                                    ) {
                                        tempArr.add(arr)
                                    }
                                }
                                setupListOfDataIntoRecyclerView(tempArr, wordDao, userId)
                                return true
                            }
                        })
                    }
                }
            }
        }
    }

    private fun setupListOfDataIntoRecyclerView(
        wordList: ArrayList<WordEntity>,
        wordDao: WordDao,
        userId: Int
    ) {
        if (wordList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(wordList,
                { changeId ->
                    lifecycleScope.launch {
                        wordDao.fetchAllWordById(changeId).collect {
                            if (it != null) {
                                changeRecordDialog(changeId, wordDao, it)
                            }
                        }
                    }
                },
                { updateId ->
                    updateRecordDialog(updateId, wordDao, userId)
                },
                { deleteId ->
                    lifecycleScope.launch {
                        wordDao.fetchAllWordById(deleteId).collect {
                            if (it != null) {
                                deleteRecordAlertDialog(deleteId, wordDao, it)
                            }
                        }
                    }
                }
            )
            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this)
            binding?.rvItemsList?.adapter = itemAdapter
            binding?.rvItemsList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        } else {
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    fun deleteRecordAlertDialog(id: Int, wordDao: WordDao, word: WordEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("删除单词")
        builder.setMessage("你确定要删除单词 - （${word.name}）.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("确定") { dialogInterface, _ ->
            GlobalScope.launch {
                wordDao.delete(WordEntity(id))
            }
            lifecycleScope.launch {
                Toast.makeText(
                    applicationContext,
                    "删除单词成功.",
                    Toast.LENGTH_SHORT
                ).show()
                dialogInterface.dismiss()
            }
        }
        builder.setNegativeButton("取消") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun addRecordDialog(wordDao: WordDao, userId: Int) {
        val addDialog = Dialog(this, R.style.Theme_Dialog)
        addDialog.setCancelable(false)
        val binding = DialogAddBinding.inflate(layoutInflater)
        addDialog.setContentView(binding.root)

        binding.tvUpdate.setOnClickListener {
            val name = binding?.etName?.text.toString()
            val explanation = binding?.etExplanation?.text.toString()
            if (name.isNotEmpty() && explanation.isNotEmpty()) {
                GlobalScope.launch {
                    wordDao.insert(
                        WordEntity(
                            id = Random.nextInt(),
                            name = name,
                            explanation = explanation,
                            userId = userId
                        )
                    )
                }
                lifecycleScope.launch {
                    Toast.makeText(applicationContext, "保存单词成功", Toast.LENGTH_SHORT).show()
                    binding?.etName?.text?.clear()
                    binding?.etExplanation?.text?.clear()
                    addDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "单词或者释义不能为空",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.tvCancel.setOnClickListener {
            addDialog.dismiss()
        }
        addDialog.show()
    }

    fun changeRecordDialog(id: Int, wordDao: WordDao, word: WordEntity) {
        GlobalScope.launch {
            wordDao.update(WordEntity(id, word.name, word.explanation, word.userId, 1))
        }
    }

    fun updateRecordDialog(id: Int, wordDao: WordDao, userId: Int) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)
        lifecycleScope.launch {
            wordDao.fetchAllWordById(id).collect {
                if (it != null) {
                    binding.etUpdateName.setText(it.name)
                    binding.etExplanation.setText(it.explanation)
                }
            }
        }
        binding.tvUpdate.setOnClickListener {
            val name = binding.etUpdateName.text.toString()
            val email = binding.etExplanation.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                GlobalScope.launch {
                    wordDao.update(WordEntity(id, name, email, userId))
                }
                lifecycleScope.launch {
                    Toast.makeText(applicationContext, "修改单词成功.", Toast.LENGTH_SHORT)
                        .show()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "单词和释义不能为空",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }
}
