package com.example.babybuy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.babybuy.Model.AuthDatamodel;
import com.example.babybuy.Model.CatDataModel;
import com.example.babybuy.Model.ProductDataModel;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {


    //final variable declaration for Database
    public static final String DB_NAME = "babybuy.db";


    //final variable declaration for Registration and Login Table
    public static final String AUTH_TABLE = "auth";
    public static final String AUTH_ID = "id";
    public static final String AUTH_COL_1 = "firstname";
    public static final String AUTH_COL_2 = "lastname";
    public static final String AUTH_COL_3 = "email";
    public static final String AUTH_COL_4 = "password";
    public static final String AUTH_COL_5 = "image";
    public static final String AUTH_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + AUTH_TABLE +
            "(" + AUTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AUTH_COL_1 + " varchar(100), "
            + AUTH_COL_2 + " varchar(100), "
            + AUTH_COL_3 + " varchar(100), "
            + AUTH_COL_4 + " varchar(100), "
            + AUTH_COL_5 + " BLOB)";


    //final variable declaration for Category Table
    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_ID = "categoryid";
    public static final String CATEGORY_COL_1 = "categoryname";
    public static final String CATEGORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE +
            "(" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_COL_1 + " varchar(100))";


    //final variable declaration for Product Table
    public static final String PRODUCT_TABLE = "product";
    public static final String PRODUCT_ID = "productid";
    public static final String PRODUCT_COL_1 = "productname";
    public static final String PRODUCT_COL_2 = "productquantity";
    public static final String PRODUCT_COL_3 = "productprice";
    public static final String PRODUCT_COL_4 = "productdescription";
    public static final String PRODUCT_COL_5 = "productstatus";
    public static final String PRODUCT_COL_6 = "productimage";
    public static final String PRODUCT_COL_7 = "productcategoryid";
    public static final String PRODUCT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE +
            "(" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PRODUCT_COL_1 + " varchar(100), "
            + PRODUCT_COL_2 + " INTEGER, "
            + PRODUCT_COL_3 + " real, "
            + PRODUCT_COL_4 + " varchar(100), "
            + PRODUCT_COL_5 + " INTEGER, "
            + PRODUCT_COL_6 + " BLOB, "
            + PRODUCT_COL_7 + " INTEGER REFERENCES " + CATEGORY_TABLE + "(" + CATEGORY_COL_1 + " ))";


    //Constructor for Database class
    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }


    //Method for table creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execute above query
        db.execSQL(AUTH_TABLE_CREATE);
        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(PRODUCT_TABLE_CREATE);
    }


    //Method for table upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AUTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        onCreate(db);
    }


    //Insert query for Registration
    public boolean insert(String firstname, String lastname, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(AUTH_COL_1, firstname);
        v.put(AUTH_COL_2, lastname);
        v.put(AUTH_COL_3, email);
        v.put(AUTH_COL_4, password);
        long res = db.insert(AUTH_TABLE, null, v);
        if (res == -1)
            return false;
        else
            return true;
    }


    //Method for preventing multiple registration using same email address
    public Boolean checkemail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getlistemail = db.rawQuery("select * from " + AUTH_TABLE + " where " +
                "email = ?", new String[]{email});
        if (getlistemail.getCount() > 0)
            return false;
        else
            return true;
    }


    //Method for login validation
    public Boolean checkemailandpassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + AUTH_TABLE + " where" +
                        " email = ? and password = ?",
                new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    // delete method for auth user delete it later
    public int deleteauthuser(int id) {
        SQLiteDatabase ddb = getWritableDatabase();
        return ddb.delete(AUTH_TABLE, "id = ?",
                new String[]{String.valueOf(id)});
    }


    //update method for user table delete it later
    public int updateauthuser(String fname, String lname, int id) {
        SQLiteDatabase udb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AUTH_COL_1, fname);
        cv.put(AUTH_COL_2, lname);
        return udb.update(AUTH_TABLE, cv, "id = ?", new String[]{String.valueOf(id)});

    }


    public String getfullname(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getdata = db.rawQuery("select * from " + AUTH_TABLE+ " where " + AUTH_COL_3 + " = ?", new String[]{email});
        String fullname = "";
        while (getdata.moveToNext()) {
            String firstname = getdata.getString(1);
            String lastname = getdata.getString(2);
            fullname = firstname + " " + lastname;
        }

        return fullname;
    }


    //insert method for category
    public long categoryadd(String catname) {
        SQLiteDatabase catdb = getWritableDatabase();
        ContentValues catv = new ContentValues();
        catv.put(CATEGORY_COL_1, catname);
        return catdb.insert(CATEGORY_TABLE, null, catv);
    }


    //select method for category
    public ArrayList<CatDataModel> categoryfetchdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getdata = db.rawQuery("select * from " + CATEGORY_TABLE, null);
        ArrayList<CatDataModel> catarray = new ArrayList<>();
        while (getdata.moveToNext()) {
            CatDataModel cat = new CatDataModel();
            cat.catid = getdata.getInt(0);
            cat.catname = getdata.getString(1);
            catarray.add(cat);
        }
        return catarray;
    }


    //update method for category
    public int updatecategoryname(String catname, int catid) {
        SQLiteDatabase udb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_COL_1, catname);
        return udb.update(CATEGORY_TABLE, cv, "categoryid = ?", new String[]{String.valueOf(catid)});
    }


    // delete method for category
    public int deletecategory(int catid) {
        SQLiteDatabase ddb = getWritableDatabase();
        return ddb.delete(CATEGORY_TABLE, CATEGORY_ID + " = ?", new String[]{String.valueOf(catid)});
    }


    //insert method for product
    public long productadd(ProductDataModel productDataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(PRODUCT_COL_1, productDataModel.getProductname());
        v.put(PRODUCT_COL_2, productDataModel.getProductquantity());
        v.put(PRODUCT_COL_3, productDataModel.getProductprice());
        v.put(PRODUCT_COL_4, productDataModel.getProductdescription());
        v.put(PRODUCT_COL_5, productDataModel.getProductstatus());
        v.put(PRODUCT_COL_6, productDataModel.getProductimage());
        v.put(PRODUCT_COL_7, productDataModel.getProductcategoryid());
        return db.insert(PRODUCT_TABLE, null, v);
    }


    //change status of product purchased or not
    public int productpurchased(int productstsid, int productid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(PRODUCT_COL_5, productstsid);
        return db.update(PRODUCT_TABLE, v,  PRODUCT_ID + " = ?", new String[]{String.valueOf(productid)});
    }


    //select method for productlist
    public ArrayList<ProductDataModel> productfetchdata(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getdata = db.rawQuery("select * from " + PRODUCT_TABLE + " where productcategoryid = ?", new String[]{String.valueOf(id)});
        ArrayList<ProductDataModel> productarray = new ArrayList<>();
        while (getdata.moveToNext()) {
            ProductDataModel prod = new ProductDataModel();
            prod.setProductid(getdata.getInt(0));
            prod.setProductname(getdata.getString(1));
            prod.setProductquantity(getdata.getInt(2));
            prod.setProductprice(getdata.getDouble(3));
            prod.setProductdescription(getdata.getString(4));
            prod.setProductstatus(getdata.getInt(5));
            prod.setProductimage(getdata.getBlob(6));

            productarray.add(prod);
        }
        return productarray;
    }


    //purchased product list for manageproduct fragment
    public ArrayList<ProductDataModel> productfetchdataforpurchased(int productsts) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getdata = db.rawQuery("select * from " + PRODUCT_TABLE + " where " + PRODUCT_COL_5 + " = ?" , new String[]{String.valueOf(productsts)});
        ArrayList<ProductDataModel> productarray = new ArrayList<>();
        while (getdata.moveToNext()) {
            ProductDataModel prod = new ProductDataModel();
            prod.setProductid(getdata.getInt(0));
            prod.setProductname(getdata.getString(1));
            prod.setProductquantity(getdata.getInt(2));
            prod.setProductprice(getdata.getDouble(3));
            prod.setProductdescription(getdata.getString(4));
            prod.setProductstatus(getdata.getInt(5));
            prod.setProductimage(getdata.getBlob(6));
            productarray.add(prod);
        }
        return productarray;
    }


    //delete product item when product delete
    public int deleteproduct(int id) {
        SQLiteDatabase ddb = getWritableDatabase();
        return ddb.delete(PRODUCT_TABLE, PRODUCT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    //delete product items when category delete
    public int deleteproductbycat(int procatid) {
        SQLiteDatabase ddb = getWritableDatabase();
        return ddb.delete(PRODUCT_TABLE, PRODUCT_COL_7 + " = ?",
                new String[]{String.valueOf(procatid)});
    }
}
