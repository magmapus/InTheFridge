package net.magmastone.inthefrige.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import net.magmastone.inthefrige.R;

import net.magmastone.inthefrige.utility.UPCAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ShoppingFragment extends Fragment implements AbsListView.OnItemClickListener {

    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private UPCAdapter mAdapter;

    public static ShoppingFragment newInstance() {
        ShoppingFragment fragment = new ShoppingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new UPCAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcitem, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void updateList(){
        Log.d("uder", "Updated List");

        mAdapter.updateList();
    }
    BroadcastReceiver rcvr= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("net.magmastone.NewItem")){
                updateList();
            }
        }
    };
    @Override
    public void onResume(){
        super.onResume();
        getActivity().registerReceiver(rcvr,new IntentFilter("net.magmastone.NewItem"));
    }
    @Override public void onDestroy(){
        super.onDestroy();

        getActivity().unregisterReceiver(rcvr);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




}
