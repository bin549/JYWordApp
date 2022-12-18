package com.android.jywordapp.activities

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.jywordapp.Constants
import com.android.jywordapp.Dao.WordDao
import com.android.jywordapp.R
import com.android.jywordapp.WordApp
import com.android.jywordapp.adapters.ItemAdapter
import com.android.jywordapp.databinding.ActivityWordEditorBinding
import com.android.jywordapp.databinding.DialogUpdateBinding
import com.android.jywordapp.model.WordEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
//        binding?.btnAdd?.setOnClickListener {
//            addRecord(wordDao, userId)
//        }
        lifecycleScope.launch {
            wordDao.fetchALlWords(userId).collect() {
                Log.d("word", "$it")
                val list = ArrayList(it)
                setupListOfDataIntoRecyclerView(list, wordDao, userId)
            }
        }
    }

    private fun setupListOfDataIntoRecyclerView(
        wordList: ArrayList<WordEntity>,
        wordDao: WordDao,
        userId: Int
    ) {
        if (wordList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(wordList, { updateId ->
                updateRecordDialog(updateId, wordDao, userId)
            }) { deleteId ->
                lifecycleScope.launch {
                    wordDao.fetchALlWordById(deleteId).collect {
                        if (it != null) {
                            deleteRecordAlertDialog(deleteId, wordDao, it)
                        }
                    }
                }
            }
//            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this)
//            binding?.rvItemsList?.adapter = itemAdapter
//            binding?.rvItemsList?.visibility = View.VISIBLE
//            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        } else {
//            binding?.rvItemsList?.visibility = View.GONE
//            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    fun deleteRecordAlertDialog(id: Int, wordDao: WordDao, word: WordEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you wants to delete ${word.name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            GlobalScope.launch {
                wordDao.delete(WordEntity(id))
            }
            lifecycleScope.launch {
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()
                dialogInterface.dismiss()
            }
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun updateRecordDialog(id: Int, wordDao: WordDao, userId: Int) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)
        lifecycleScope.launch {
            wordDao.fetchALlWordById(id).collect {
                if (it != null) {
                    binding.etUpdateName.setText(it.name)
                    binding.etUpdateEmailId.setText(it.explanation)
                }
            }
        }
        binding.tvUpdate.setOnClickListener {
            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                GlobalScope.launch {
                    wordDao.update(WordEntity(id, name, email, userId))
                }
                lifecycleScope.launch {
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG)
                        .show()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Email cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }


//    fun addRecord(wordDao: WordDao, userId: Int) {
//        val name = binding?.etName?.text.toString()
//        val email = binding?.etEmailId?.text.toString()
//        if (name.isNotEmpty() && email.isNotEmpty()) {
//            GlobalScope.launch {
//                wordDao.insert(
//                    WordEntity(
//                        id = Random.nextInt(),
//                        name = name,
//                        explanation = email,
//                        userId = userId
//                    )
//                )
//            }
//            lifecycleScope.launch {
//                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
//                binding?.etName?.text?.clear()
//                binding?.etEmailId?.text?.clear()
//            }
//        } else {
//            Toast.makeText(
//                applicationContext,
//                "Name or Email cannot be blank",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
}
