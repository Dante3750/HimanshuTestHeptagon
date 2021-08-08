package com.example.himanshutestheptagon.ui.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.himanshutestheptagon.data.model.Question
import com.example.himanshutestheptagon.data.model.Value
import com.example.himanshutestheptagon.databinding.ListItemTestBinding
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


internal class TestRecyclerAdapter(val mListener: (Question) -> Unit) :
    RecyclerView.Adapter<TestRecyclerAdapter.TestAdapterViewHolder>() {


    var list1: ArrayList<Question> = arrayListOf()
    var context: Context? = null
    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestAdapterViewHolder {
        val binding =
            ListItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TestAdapterViewHolder(binding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    @SuppressLint("LogNotTimber", "SetTextI18n")
    override fun onBindViewHolder(holder: TestAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }


    private fun getItem(position: Int): Question {
        return list1[position]
    }

    fun getItems(): java.util.ArrayList<Question> {
        return list1
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return list1.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    /**
     * Swap function to set new data on updating
     */
    fun replaceItems(items: ArrayList<Question>) {
        list1 = items
        notifyDataSetChanged()
    }

    inner class TestAdapterViewHolder(val dataBinding: ListItemTestBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        init {
            dataBinding.root.setOnClickListener {
                mListener(getItem(adapterPosition))
            }
        }

        fun onBind(data: Question, position: Int) {

            val binding = dataBinding
            binding.mylist = data

            if (data.type=="text" ||data.type=="emailtext"){
                binding.etAnswers.visibility = View.VISIBLE
                binding.ivLoad.visibility = View.GONE

            }
            else if (data.type=="radio"){
                binding.etAnswers.visibility = View.GONE
                binding.ivLoad.visibility = View.GONE
                binding.rgList.removeAllViews()
                binding.llField.removeAllViews()
                for (element in data.values) {
                    addRadioButtonLive(binding,element)
                    addRadioDates(binding,element)
                }
            }

            else if (data.type=="multiselect"){
                binding.llField.removeAllViews()
                binding.etAnswers.visibility = View.GONE
                binding.ivLoad.visibility = View.GONE
                for (element in data.values) {
                    addCheckBoxLive(binding,element)
                }
            }

            else if (data.type=="dropdown") {
                binding.llField.removeAllViews()
                binding.etAnswers.visibility = View.GONE
                binding.ivLoad.visibility = View.GONE
                val list = ArrayList<String>()
                for (element in data.values) {
                    list.add(element.value)
                }
                addDropDownLive(binding, list)
            }

            else if (data.type=="imageview") {
                binding.etAnswers.visibility = View.GONE
                binding.llField.visibility = View.GONE
            }
            else{
                binding.etAnswers.visibility = View.GONE
            }


            /**
             * function to test data in unit test
             */
            fun addItemsForUnitTest(items: ArrayList<Question>) {
                list1 = items
            }

        }


        private fun addDropDownLive(binding: ListItemTestBinding, element: ArrayList<String>) {
            val spinner = Spinner(context)
            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, element)
            spinner.adapter = arrayAdapter
            binding.llField.addView(spinner)
        }

        private fun addCheckBoxLive(binding: ListItemTestBinding, element: Value) {
            val checkBox = CheckBox(context)
            val textString  = element.value
            checkBox.text = textString
            checkBox.layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                val msg =
                    "You have " + (if (isChecked) "checked $textString" else "unchecked $textString") + " this Check it Checkbox."
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }

            // Add Checkbox to LinearLayout

            // Add Checkbox to LinearLayout
            if (binding.llField != null) {
                binding.llField.orientation = LinearLayout.VERTICAL
                binding.llField.addView(checkBox)
            }

        }

        private fun addRadioDates(binding: ListItemTestBinding, element: Value) {
            val et = EditText(context)
            val p = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f

            )
            et.layoutParams = p
            et.id = View.generateViewId()
            val textString : String = element.value.toString()
            et.setText(textString)
            et.isEnabled = true
            val filter =
                InputFilter { source, start, end, dest, dstart, dend ->
                    for (i in start until end) {
                        if (!Pattern.compile("[1234567890]*")
                                .matcher(
                                    source[i].toString()
                                ).matches()
                        ) {
                            return@InputFilter ""
                        }
                    }
                    null
                }
            et.filters = arrayOf<InputFilter>(filter, LengthFilter(4))
            et.setBackgroundResource(R.drawable.editbox_background)
            et.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context!!,R.drawable.ic_menu_my_calendar), null)
            binding.llField.addView(et)
        }

        private fun addRadioButtonLive(binding: ListItemTestBinding, element: Value) {
            val rbn = RadioButton(context)
            rbn.id = View.generateViewId()
            val textString  = element.value
            rbn.text = textString
            binding.rgList.addView(rbn)
        }


    }

}