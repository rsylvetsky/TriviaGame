package com.example.triviagame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triviagame.model.Trivia

//class TriviaAdapter : ListAdapter<Trivia, TriviaAdapter.ViewHolder>() {
//
//
//    /**
//     * Provide a reference to the type of views that you are using
//     * (custom ViewHolder).
//     */
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        //this is null because this is not in the adapter xml, it's in the parent view
//        val button: Button = view.findViewById(R.id.button)
//
//        init {
//            // Define click listener for the ViewHolder's View.
//        }
//    }
//
//
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.answer_option_item, viewGroup, false)
//
//       val button = view.findViewById<Button>(R.id.button)
//        val textView = view.findViewById<TextView>(R.id.result_message)
//
//        return ViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//
//
//        val current = getItem(position)
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
////        viewHolder.button.text = current.
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() =
//
//    fun scrambleQuestions(trivia: Trivia) : MutableList<String> {
//        var potentialAnswers: MutableList<String> = mutableListOf()
//
//        potentialAnswers.add(trivia.correctAnswer)
//        trivia.incorrectAnswers.map{ potentialAnswers.add(it)}
//
//        potentialAnswers.shuffle()
//        return potentialAnswers
//    }
//}