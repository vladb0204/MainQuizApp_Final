package com.education.android.mainquizapp_final

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import java.util.*

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "question_id"

class QuestionFragment : Fragment() {

    private lateinit var question: Question
    private lateinit var titleField: TextView
    private lateinit var solvedCheckBox: CheckBox

    private val questionDetailViewModel: QuestionDetailViewModel by lazy {
        ViewModelProvider(this).get(QuestionDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        question = Question()
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        titleField = view.findViewById(R.id.question_title) as TextView
        solvedCheckBox = view.findViewById(R.id.question_solved) as CheckBox

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionDetailViewModel.questionLiveData.observe (
            viewLifecycleOwner, Observer { question -> question?.let { this.question = question; updateUI() } }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                question.title = s.toString();
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question.title = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }
        }

        titleField.addTextChangedListener(titleWatcher)

        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked -> question.isSolved = true }
        }
    }

    override fun onStop() {
        super.onStop()
        questionDetailViewModel.saveQuestion(question)
    }

    private fun updateUI() {
        titleField.setText(question.title)
        solvedCheckBox.isChecked = question.isSolved
        solvedCheckBox.apply {
            isChecked = question.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    companion object {
        fun newInstance(crimeId: UUID): QuestionFragment {
            val args = Bundle().apply { putSerializable(ARG_CRIME_ID, crimeId) }
            return QuestionFragment().apply { arguments = args }
        }
    }
}