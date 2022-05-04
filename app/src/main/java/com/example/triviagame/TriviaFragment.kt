package com.example.triviagame

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.example.triviagame.databinding.FragmentTriviaBinding
import com.example.triviagame.model.TriviaStatus
import com.example.triviagame.ui.TriviaViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TriviaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TriviaFragment : Fragment(R.layout.fragment_trivia), MavericksView {

    // TODO add file from Mike
//    private val binding: FragmentTriviaBinding by viewBinding()

    private val triviaViewModel: TriviaViewModel by activityViewModel()
    private var question : TextView? = null
    private var answerOne : Button? = null
    private var answerTwo : Button?= null
    private var answerThree : Button?= null
    private var answerFour : Button? = null
    private var resultMessage : TextView? =null
    private var nextQuestion : Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = view.findViewById(R.id.question_text)
        answerOne = view.findViewById(R.id.answer_1)
        answerTwo = view.findViewById(R.id.answer_2)
        answerThree = view.findViewById(R.id.answer_3)
        answerFour = view.findViewById(R.id.answer_4)
        resultMessage =view.findViewById(R.id.result_message)
        nextQuestion = view.findViewById(R.id.next_question_button)

        answerOne?.setOnClickListener { triviaViewModel.submitAnswer(0) }
        answerTwo?.setOnClickListener {triviaViewModel.submitAnswer(1)  }
        answerThree?.setOnClickListener { triviaViewModel.submitAnswer(2) }
        answerFour?.setOnClickListener { triviaViewModel.submitAnswer(3) }

    }
    override fun invalidate() {
        return withState(triviaViewModel) { state ->
            question?.text = Html.fromHtml(state.question)
            answerOne?.text = Html.fromHtml(state.answers[0])
            answerTwo?.text = Html.fromHtml(state.answers[1])
            answerThree?.text = Html.fromHtml(state.answers[2])
            answerFour?.text = Html.fromHtml(state.answers[3])

            //Async for error handling
            //suspend

            when (state.answerState) {
                TriviaStatus.ANSWERED_CORRECTLY -> {
                    resultMessage?.visibility = View.VISIBLE
                    resultMessage?.text = "Correct"
                }
                TriviaStatus.ANSWERED_INCORRECTLY -> {
                    resultMessage?.visibility = View.VISIBLE
                    resultMessage?.text = "Try again"
                }
                else -> {
                    resultMessage?.visibility = View.GONE
                }
            }
        }
    }

}