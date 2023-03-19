package com.example.p0461_expandablelistevents;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdapterHelper {

    private final String ATTR_GROUP_NAME = "groupName";
    private final String ATTR_PHONE_NAME = "phoneName";

    private SimpleExpandableListAdapter adapter;

    public AdapterHelper(Context context) {
//       названия компаний (групп)
        String[] groups = context.getResources().getStringArray(R.array.groups);
//       названия телефонов (элементов)
        String[] phonesHTC = context.getResources().getStringArray(R.array.phoneHTC);
        String[] phonesSamsung = context.getResources().getStringArray(R.array.phoneSamsung);
        String[] phonesLG = context.getResources().getStringArray(R.array.phoneLG);
//        коллекция для групп
        List<Map<String, String>> groupData = fillInTheList(groups, ATTR_GROUP_NAME);

//        коллекция для элементов первой группы
        List<Map<String, String>> childDataItem1 = fillInTheList(phonesHTC, ATTR_PHONE_NAME);
//        коллекция для элементов второй группы
        List<Map<String, String>> childDataItem2 = fillInTheList(phonesSamsung, ATTR_PHONE_NAME);
//        коллекция для элементов третьей группы
        List<Map<String, String>> childDataItem3 = fillInTheList(phonesLG, ATTR_PHONE_NAME);

//        общая коллекция для коллекций элементов
        List<List<Map<String, String>>> childData = new ArrayList<>();
        childData.add(childDataItem1);
        childData.add(childDataItem2);
        childData.add(childDataItem3);

//        список аттрибутов групп для чтения
        String[] groupFrom = { ATTR_GROUP_NAME };
//        список ID view-элементов, в которые будет помещены аттрибуты групп
        int[] groupTo = { android.R.id.text1 };

//        список аттрибутов элементов для чтения
        String[] childFrom = { ATTR_PHONE_NAME };
//        список ID view-элементов, в которые будет помещены аттрибуты элементов
        int[] childTo = { android.R.id.text1 };

        adapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo
        );
    }

    public SimpleExpandableListAdapter getAdapter() {
        return adapter;
    }

    String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get(ATTR_GROUP_NAME);
    }

    String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(ATTR_PHONE_NAME);
    }

    String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }

    private List<Map<String, String >> fillInTheList(String[] values, String ATTR_NAME) {
        return Arrays.stream(values).map(value -> {
            Map<String, String> map = new HashMap<>();
            map.put(ATTR_NAME, value);
            return map;
        }).collect(Collectors.toList());
    }
}
