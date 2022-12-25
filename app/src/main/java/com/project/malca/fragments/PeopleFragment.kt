package com.project.malca.fragments

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.malca.ChatActivity
import com.project.malca.IMAGE
import com.project.malca.MainActivity
import com.project.malca.NAME
import com.project.malca.R
import com.project.malca.UID
import com.project.malca.adapters.EmptyViewHolder
import com.project.malca.adapters.UserViewHolder
import com.project.malca.models.User
import com.project.malca.utils.SearchViewModel
import java.util.Locale

private const val DELETED_VIEW_TYPE = 1
private const val NORMAL_VIEW_TYPE = 2
lateinit var mAdapter: FirestorePagingAdapter<User, RecyclerView.ViewHolder>

class PeopleFragment : Fragment() {
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    val database by lazy {
        FirebaseFirestore.getInstance().collection("users")
            .orderBy("rating", Query.Direction.DESCENDING)
    }
    var tabSelected = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupAdapter()
        val view = inflater.inflate(com.project.malca.R.layout.fragment_people, container, false)
        val btnAddFilter =
            view?.findViewById<FloatingActionButton>(com.project.malca.R.id.btnAddFilter)
        btnAddFilter!!.setOnClickListener {
            showPopup(it)
        }
        return view
    }

    private fun showPopup(anchorView: View) {
        val layout = layoutInflater.inflate(R.layout.activity_filter, null)
        val layoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        val popupWindow = PopupWindow(layout, 580, 900, true)
        popupWindow.isOutsideTouchable = false
        val radioGroup = layout.findViewById<RadioGroup>(R.id.radioGrp)
        val closeBtn = layout.findViewById<ImageView>(com.project.malca.R.id.closeBtn)
        val skillsTab = layout.findViewById<TextView>(com.project.malca.R.id.skillsTab)
        val companyTab = layout.findViewById<TextView>(com.project.malca.R.id.companyTab)
        val locationTab = layout.findViewById<TextView>(com.project.malca.R.id.locationTab)
        if (tabSelected.isEmpty()) {
            val count = radioGroup.childCount
            if (count > 0) {
                for (i in count - 1 downTo 0) {
                    val o = radioGroup.getChildAt(i)
                    if (o is RadioButton) {
                        radioGroup.removeViewAt(i)
                    }
                }
            }
            val skillsArray = resources.getStringArray(R.array.skillsTab)
            for (i in skillsArray.indices) {
                val radioButton = RadioButton(requireContext())
                radioButton.text = skillsArray[i]
                radioButton.buttonTintList =
                    ColorStateList.valueOf(getColor(requireContext(), R.color.purple_500))
                radioButton.setPadding(10, 10, 0, 10)
                radioButton.textSize = 16F
                radioButton.id = i
                radioGroup.addView(radioButton)
            }
            tabSelected = "skills"
        }
        skillsTab.setOnClickListener {
            if (tabSelected != "skills") {
                val count = radioGroup.childCount
                if (count > 0) {
                    for (i in count - 1 downTo 0) {
                        val o = radioGroup.getChildAt(i)
                        if (o is RadioButton) {
                            radioGroup.removeViewAt(i)
                        }
                    }
                }
                val skillsArray = resources.getStringArray(R.array.skillsTab)
                for (i in skillsArray.indices) {
                    val radioButton = RadioButton(requireContext())
                    radioButton.text = skillsArray[i]
                    radioButton.buttonTintList =
                        ColorStateList.valueOf(getColor(requireContext(), R.color.purple_500))
                    radioButton.setPadding(10, 5, 0, 5)
                    radioButton.textSize = 16F
                    radioButton.id = i
                    radioGroup.addView(radioButton)
                }
                tabSelected = "skills"
            }

        }

        companyTab.setOnClickListener {
            if (tabSelected != "company") {
                val count = radioGroup.childCount
                if (count > 0) {
                    for (i in count - 1 downTo 0) {
                        val o = radioGroup.getChildAt(i)
                        if (o is RadioButton) {
                            radioGroup.removeViewAt(i)
                        }
                    }
                }
                val companyArray = resources.getStringArray(R.array.companyTab)
                for (i in companyArray.indices) {
                    val radioButton = RadioButton(requireContext())
                    radioButton.text = companyArray[i]
                    radioButton.buttonTintList =
                        ColorStateList.valueOf(getColor(requireContext(), R.color.purple_500))
                    radioButton.setPadding(10, 5, 0, 5)
                    radioButton.textSize = 16F
                    radioButton.id = i
                    radioGroup.addView(radioButton)
                }
                tabSelected = "company"
            }
            locationTab.setOnClickListener {
                if (tabSelected != "location") {
                    val count = radioGroup.childCount
                    if (count > 0) {
                        for (i in count - 1 downTo 0) {
                            val o = radioGroup.getChildAt(i)
                            if (o is RadioButton) {
                                radioGroup.removeViewAt(i)
                            }
                        }
                    }
                    val locationArray = resources.getStringArray(R.array.locationTab)
                    for (i in locationArray.indices) {
                        val radioButton = RadioButton(requireContext())
                        radioButton.text = locationArray[i]
                        radioButton.buttonTintList =
                            ColorStateList.valueOf(getColor(requireContext(), R.color.purple_500))
                        radioButton.setPadding(10, 5, 0, 5)
                        radioButton.textSize = 16F
                        radioButton.id = i
                        radioGroup.addView(radioButton)
                    }
                    tabSelected = "location"
                }

            }
        }
        popupWindow.setOnDismissListener {
            tabSelected = ""
        }
        closeBtn.setOnClickListener {
            initialAdapter()
            popupWindow.dismiss()
        }
        radioGroup.setOnCheckedChangeListener { _, _ ->
            val checkedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            val radioBtn = layout.findViewById(checkedRadioButtonId) as RadioButton
            Log.d("PeopleFragment", "showPopup: ${radioBtn.text}")
            when (radioBtn.text) {
                "None" -> {
                    initialAdapter()
                }
                else -> {
                    changeAdapter(radioBtn.text.toString())
                }
            }
        }
        popupWindow.showAtLocation(layout, Gravity.BOTTOM or Gravity.END, 70, 240)
        popupWindow.isFocusable = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.project.malca.R.menu.main_menu, menu)
        val myActionMenuItem = menu.findItem(R.id.search)
        val sv = SearchView((activity as MainActivity?)!!.supportActionBar!!.themedContext)
        MenuItemCompat.setShowAsAction(
            myActionMenuItem,
            MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
        )
        MenuItemCompat.setActionView(myActionMenuItem, sv)
        sv.setIconifiedByDefault(true)
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.setQuery(newText!!)
                return false
            }
        })
        myActionMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {

                Log.d("SearchView", "Closed")
                return true
            }
        })
    }

    private fun changeAdapterQuery(query: String) {
        Log.d("SearchView", query.uppercase(Locale.getDefault()))
        val filterQuery = FirebaseFirestore.getInstance().collection("users").orderBy("upper_name")
            .startAt(query.uppercase(Locale.getDefault())).endAt(query.uppercase(Locale.getDefault()) + "\uf8ff")
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10)
            .setPrefetchDistance(2).build()
        val options = FirestorePagingOptions.Builder<User>().setLifecycleOwner(viewLifecycleOwner)
            .setQuery(filterQuery, config, User::class.java).build()
        mAdapter.updateOptions(options)
    }

    private fun changeAdapter(value: String) {
        var filterQuery: Query? = null
        when (value) {
            in resources.getStringArray(R.array.skillsTab) -> {
                filterQuery = FirebaseFirestore.getInstance().collection("users")
                    .whereArrayContains("skills", value)
                    .orderBy("rating", Query.Direction.DESCENDING)
            }
            in resources.getStringArray(R.array.companyTab) -> {
                filterQuery = FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("company", value).orderBy("rating", Query.Direction.DESCENDING)
            }
            in resources.getStringArray(R.array.locationTab) -> {
                filterQuery = FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("country", value).orderBy("rating", Query.Direction.DESCENDING)
            }
        }

        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10)
            .setPrefetchDistance(2).build()
        val options = FirestorePagingOptions.Builder<User>().setLifecycleOwner(viewLifecycleOwner)
            .setQuery(filterQuery!!, config, User::class.java).build()
        mAdapter.updateOptions(options)
    }

    private fun setupAdapter() {
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10)
            .setPrefetchDistance(2).build()
        val options = FirestorePagingOptions.Builder<User>().setLifecycleOwner(viewLifecycleOwner)
            .setQuery(database, config, User::class.java).build()
        mAdapter = object : FirestorePagingAdapter<User, RecyclerView.ViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup, viewType: Int
            ): RecyclerView.ViewHolder {
                return when (viewType) {
                    NORMAL_VIEW_TYPE -> UserViewHolder(
                        layoutInflater.inflate(
                            R.layout.list_item_people, parent, false
                        )
                    )
                    else -> EmptyViewHolder(
                        layoutInflater.inflate(
                            R.layout.empty_view, parent, false
                        )
                    )
                }
            }

            override fun getItemViewType(position: Int): Int {
                val item = getItem(position)?.toObject(User::class.java)
                return if (auth.uid == item!!.uid) {
                    DELETED_VIEW_TYPE
                } else {
                    NORMAL_VIEW_TYPE
                }
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                    }
                    LoadingState.LOADING_MORE -> {
                    }
                    LoadingState.LOADED -> {
                    }
                    LoadingState.FINISHED -> {
                    }
                    LoadingState.ERROR -> {
                    }
                }
            }

            override fun onBindViewHolder(
                holder: RecyclerView.ViewHolder, position: Int, model: User
            ) {
                if (holder is UserViewHolder) {
                    if (model.skills.size == 1) {
                        holder.itemView.findViewById<RelativeLayout>(com.project.malca.R.id.skillLayout2).visibility =
                            View.GONE
                        holder.itemView.findViewById<RelativeLayout>(com.project.malca.R.id.skillLayout3).visibility =
                            View.GONE
                    }
                    if (model.skills.size == 2) {
                        holder.itemView.findViewById<RelativeLayout>(com.project.malca.R.id.skillLayout3).visibility =
                            View.GONE
                    }
                    holder.bind(user = model) { name: String, photo: String, id: String ->
                        val intent = Intent(requireContext(), ChatActivity::class.java)
                        intent.putExtra(UID, id)
                        intent.putExtra(NAME, name)
                        intent.putExtra(IMAGE, photo)
                        startActivity(intent)
                    }
                } else {

                }
            }
        }
    }

    fun initialAdapter() {
        val filterQuery = FirebaseFirestore.getInstance().collection("users")
            .orderBy("rating", Query.Direction.DESCENDING)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10)
            .setPrefetchDistance(2).build()
        val options =
            FirestorePagingOptions.Builder<User>().setQuery(filterQuery, config, User::class.java)
                .build()
        mAdapter.updateOptions(options)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(com.project.malca.R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        val searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        searchViewModel.getQuery()!!.observe(
            viewLifecycleOwner
        ) { t ->
            if (t != null) {
                changeAdapterQuery(t)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}

