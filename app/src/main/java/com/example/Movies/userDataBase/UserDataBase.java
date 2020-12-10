package.com.example.movies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
public class DBHelper extends SQLiteOpenHelper {
public static final String DATABASE_NAME = "MyDBName.db";
public static final String USER_TABLE_NAME = "user";
public static final String USER_COLUMN_ID = "id";
public static final String USER_COLUMN_NAME = "name";
public static final String USER_COLUMN_EMAIL = "email";
public static final String USER_COLUMN_PASSWORD = "street";
public static final String USER_COLUMN_PHONE = "phone";
public static final String USER_COLUMN_MOVIEFAVOURITES = "movie_favourites";
public static final String USER_COLUMN_MOVIEBOOKED = "movie_booked";
private HashMap hp;
public DBHelper(Context context) {
super(context, DATABASE_NAME , null, 1);
}
@Override
public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
db.execSQL(
"create table user " +
"(id integer primary key, name text,phone text,email text, password text, movie_favourites text,
movie_booked text)"
);
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS user");
onCreate(db);
}
public boolean insertData (String name, String phone, String email, String password) {
SQLiteDatabase db = this.getWritableDatabase();

ContentValues contentValues = new ContentValues();
contentValues.put("name", name);
contentValues.put("phone", phone);
contentValues.put("email", email);
contentValues.put("password", password);
contentValues.put("favourites", movie_favourites);
contentValues.put("booked", movie_booked);
db.insert("user", null, contentValues);
return true;
}
public Cursor getData(int id) {
SQLiteDatabase db = this.getReadableDatabase();
Cursor res = db.rawQuery( "select * from user where id="+id+"", null );
return res;
}
public int numberOfRows(){
SQLiteDatabase db = this.getReadableDatabase();
int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
return numRows;
}
public boolean updateUser (Integer id, String name, String phone, String email, String password) {
SQLiteDatabase db = this.getWritableDatabase();
ContentValues contentValues = new ContentValues();
contentValues.put("name", name);
contentValues.put("phone", phone);
contentValues.put("email", email);
contentValues.put("password", passoword);
contentValues.put("favourites", movie_favourites);
contentValues.put("booked", movie_booked);
db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
return true;
}
public Integer deleteUser (Integer id) {
SQLiteDatabase db = this.getWritableDatabase();
return db.delete("contacts",
"id = ? ",
new String[] { Integer.toString(id) });
}
public ArrayList<String> getAllUser() {
ArrayList<String> array_list = new ArrayList<String>();
//hp = new HashMap();
SQLiteDatabase db = this.getReadableDatabase();
Cursor res = db.rawQuery( "select * from user", null );

res.moveToFirst();
while(res.isAfterLast() == false){
array_list.add(res.getString(res.getColumnIndex(USER_COLUMN_NAME)));
res.moveToNext();
}
return array_list;
}
}
