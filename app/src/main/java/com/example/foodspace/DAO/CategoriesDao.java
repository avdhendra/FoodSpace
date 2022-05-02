package com.example.foodspace.DAO;

import com.example.foodspace.Constants;
import com.example.foodspace.Model.PopularFoodItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CategoriesDao {
    Constants constants=new Constants();
    private DatabaseReference databaseReference;
    public CategoriesDao(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child(constants.CATGORIES);
    }
    public Query get(){
        return databaseReference.orderByKey();

    }

}
