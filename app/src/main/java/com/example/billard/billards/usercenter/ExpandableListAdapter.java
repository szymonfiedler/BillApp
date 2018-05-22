package com.example.billard.billards.usercenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billard.billards.R;
import com.example.billard.billards.authorization.JSONfunction;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private boolean rm = false;
    public static Button cnl;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.list_item, null);

        }

        String childText1, childText2, childText3, childText4;
        childText1 = childText.substring(0, childText.indexOf("Typ"));
        childText2 = childText.substring(childText.indexOf("Typ"), childText.indexOf("Data"));
        childText3 = childText.substring(childText.indexOf("Data") + 4, childText.indexOf("Opłata"));
        String day, month, year, rest;
        day = childText3.substring(8, 10);
        year = childText3.substring(0, 4);
        month = childText3.substring(5, 7);
        rest = childText3.substring(11);
        childText4 = childText.substring(childText.indexOf("Opłata") + 6);
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.Table);
        TextView txtListChild2 = (TextView) convertView
                .findViewById(R.id.lblListItem);
        TextView txtListChild3 = (TextView) convertView
                .findViewById(R.id.lblListItem2);
        TextView txtListChildH = (TextView) convertView.findViewById(R.id.lblListItem3);

        txtListChild.setText(childText1);
        txtListChild2.setText(childText2);
        txtListChild3.setText(day + "." + month + "." + year + "r." + "\n" + rest);
        txtListChildH.setText(childText4);
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String Today, now;
        Today = df.format(today.getTime());
        now = sdf.format(cal.getTime());
        String finalToday = Today;

        int finalI1 = UserReservations.lst[groupPosition];
        Button cnl = (Button) convertView.findViewById(R.id.cnl);
        cnl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (JSONfunction.DATE[finalI1].equals(finalToday)) {
                    int Now = Integer.parseInt(now.substring(0, 2));

                    if (JSONfunction.HOUR_FROM[finalI1] - Now < 6) {
                        Toast.makeText(_context.getApplicationContext(), "Nie można usunąć rezerwacji! ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    showDialog3("Rezerwacje", "Czy napewno chcesz usunąc?", finalI1);
                    if (rm) {
                        new UserReservations(finalI1);
                        if (UserReservations.finish) {
                            ExpandableListAdapter.this.showDialog("Rezerwacje", "Usunięto rezerwacje!");
                        }
                    }
                }
            }
        });
        return convertView;

    }

    private void showDialog3(String mas1, String mas2, int finalI1) {

        final Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_question);
        Button dialog_btn = (Button) dialog.findViewById(R.id.tak);
        Button dialog_btn2 = (Button) dialog.findViewById(R.id.nie);
        TextView title = (TextView) dialog.findViewById(R.id.text1);
        TextView text = (TextView) dialog.findViewById(R.id.text2);
        title.setText(mas1);
        text.setText(mas2);
        Window window = dialog.getWindow();
        assert window != null;
        Activity activity = (Activity) _context;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        dialog.show();

        dialog_btn.setOnClickListener(view -> {
            rm = true;
            ExpandableListAdapter.this.showDialog("Rezerwacje", "Usunięto rezerwacje!");
            new UserReservations(finalI1);
            dialog.dismiss();
        });
        dialog_btn2.setOnClickListener(view -> {
            rm = false;
            dialog.dismiss();
        });
        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                rm = false;
                dialog.dismiss();
            }
            return true;
        });

    }


    private void showDialog(String mas1, String mas2) {

        final Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok);
        Button dialog_btn = (Button) dialog.findViewById(R.id.ok2);
        TextView title = (TextView) dialog.findViewById(R.id.text1);
        TextView text = (TextView) dialog.findViewById(R.id.text2);
        title.setText(mas1);
        text.setText(mas2);
        Window window = dialog.getWindow();
        assert window != null;
        Activity activity = (Activity) _context;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        dialog.show();

        dialog_btn.setOnClickListener(view -> {
            Intent intent = new Intent(_context, Home.class);

            activity.startActivity(intent);

            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            activity.finish();
        });

        dialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                Intent intent = new Intent(_context, Home.class);
                activity.startActivity(intent);

                activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

                dialog.dismiss();
                activity.finish();
            }
            return true;
        });

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        convertView.setPadding(0, 20, 0, 0);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}