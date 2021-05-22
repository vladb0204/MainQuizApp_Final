package com.education.android.mainquizapp_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "CrimeListFragment"

class QuestionListFragment : Fragment() {

    interface Callbacks {
        fun onQuestionSelected(crimeId: UUID)
    }

    private lateinit var questionRecyclerView: RecyclerView

    private var callbacks: Callbacks? = null
    private var adapter: QuestionAdapter? = QuestionAdapter(emptyList())

    private val crimeListViewModel: QuestionListViewModel by lazy {
        ViewModelProvider(this).get(QuestionListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question_list, container, false)

        questionRecyclerView = view.findViewById(R.id.question_recycler_view) as RecyclerView
        questionRecyclerView.layoutManager = LinearLayoutManager(context)
        questionRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeListViewModel.questionListLiveData?.observe(viewLifecycleOwner, Observer { questions -> questions?.let {
            Log.i(TAG, "Got crimes ${questions.size}")
            updateUI(questions)
        } }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_question_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_question -> {
                val question = Question()
                crimeListViewModel.addQuestion(question)
                callbacks?.onQuestionSelected(question.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class QuestionHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var question: Question

        private val titleTextView: TextView = itemView.findViewById(R.id.question_title)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.question_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(question: Question) {
            this.question = question
            titleTextView.text = this.question.title
            solvedImageView.visibility =
                if (question.isSolved) { View.VISIBLE } else { View.GONE }
        }

        override fun onClick(v: View?) {
            callbacks?.onQuestionSelected(question.id)
        }
    }

    private inner class QuestionAdapter(var crimes: List<Question>)
        : RecyclerView.Adapter<QuestionHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
            val view = layoutInflater.inflate(R.layout.list_item_question, parent, false)
            return QuestionHolder(view)
        }

        override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int = crimes.size

    }

    private fun updateUI(crimes: List<Question>) {
        adapter = QuestionAdapter(crimes)
        questionRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): QuestionListFragment {
            return QuestionListFragment()
        }
    }
}