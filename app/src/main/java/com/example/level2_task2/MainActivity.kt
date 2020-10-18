package com.example.level2_task2

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level2_task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews(){
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter

        binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))


        createItemTouchHelper().attachToRecyclerView(binding.rvQuestions)
        addQuestions()
    }

    private fun addQuestions(){
        questions.add(Question("A cow is an animal", true))
        questions.add(Question("An animal is a cow", false))
        questions.add(Question("In Kotlin 'when' replaces the 'switch' operator in Java", true))
        questions.add(Question("You swipe right for false answers in this program", false))
        questionAdapter.notifyDataSetChanged()
    }

    private fun createItemTouchHelper(): ItemTouchHelper{
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    if (!questions[position].answer){
                        questions.removeAt(position)
                        questionAdapter.notifyDataSetChanged()
                    }
                    if (questions[position].answer){
                        Snackbar.make(binding.qzInstructions, "Wrong!", Snackbar.LENGTH_SHORT).show()
                        questionAdapter.notifyDataSetChanged()
                    }
                }

                if (direction == ItemTouchHelper.RIGHT) {
                    if (!questions[position].answer){
                        Snackbar.make(binding.qzInstructions, "Wrong!", Snackbar.LENGTH_SHORT).show()
                        questionAdapter.notifyDataSetChanged()
                    }
                    if (questions[position].answer){
                        questions.removeAt(position)
                        questionAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}