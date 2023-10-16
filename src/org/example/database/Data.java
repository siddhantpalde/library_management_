package org.example.database;


import org.example.entity.*;
import org.example.service.*;
import org.example.utility.InputReader;

import java.sql.*;
import java.util.*;

public class Data {
    static LibrarianService librarianService = new LibrarianServiceImpl();
    static DatabaseSingleton databaseSingleton = DatabaseSingleton.getInstance();
    static Connection connection = databaseSingleton.getConnection();
    private static List<Book> books = new ArrayList<>();
    private static List<Author> authors = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    private static Map<String,User> issueBook = new HashMap<String,User>();

    private static Map<String , Integer> bookQuantity = new HashMap<String,Integer>();
    public static List<Book> getBooks() {
        return books;
    }

    //    public static Double getBookPrice(){ return books.bookPrice;}
    public static Book addBookToDb(Book book) {
        try {
            books.add(book);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return book;
    }
    public static List<Author> getAuthors() {
        return authors;
    }

    public static void setAuthors(List<Author> authors) {
        Data.authors = authors;
    }

    public static void printBooks() {
        for(Book book : books) {
            System.out.println(book);
        }
    }

    public static User addUser(User user) {
        users.add(user);
        return user;
    }

    public static List<User> getUsers() {
        return users;
    }
    public static void delBook(int index) throws SQLException {
        AdminService adminService = new AdminServiceImpl();
        books.remove(index);
        System.out.print("Book Deleted successfully !!!!!");
        adminService.menu();
    }


    public static Map<String, User> getIssueBook() {
        return issueBook;
    }

    public static void setIssueBook(Map<String, User> issueBook) {
        Data.issueBook = issueBook;
    }

    public static void printIssueBook(){
        if(issueBook.size() == 0){
            System.out.println("No Books Issued ");
        }else {
            for (Map.Entry<String, User> entry : issueBook.entrySet()){
                System.out.println("Book id : "+ entry.getKey()+"\nStudent\nName : "+entry.getValue());
            }
        }
    }
    public static Boolean checkUserByEmail(String email){
        boolean flag = false;
        for(User user : users){
            if((email).equals(user.getEmailId())){
                System.out.println("User already present with same Email Id");
                return flag = true;
            }
        }
        return false;
    }
//    public static void setIssueBook(String bookId, User user) {
//        if(searchBook(bookId) == null){
//            issueBook.put(bookId,user);
//        }else {
//            System.out.println("Book already Issued use another Book");
//        }
//    }
//    private static boolean searchBook(String key) {
//        for (Map.Entry<String,User> entry : issueBook.entrySet()){
//            if((entry.getKey()).equals(key)){
//                return true;
//            }
//        }
//        return false;
//    }

    public static void removeUser(String userId){
        String choice;
        User user1 = null;
        for (User user : users){
            if ((user.getUserId()).equals(userId)){
                user1 = user;
            }
        }
        System.out.println("Do you want to delete this User : (y/n)\n"
                +"User Name : "+user1.getUsername()
                +"\nEmail Id : "+user1.getEmailId()
                +"\nUser Type : "+user1.getRole());
        choice = InputReader.getString();
        if( choice.charAt(0) == 'y' || choice.charAt(0) == 'Y' ){
            delUser(user1);
        }
    }
    private static void delUser(User user){
        users.remove(user);
        System.out.println("User Succesfully removed");
    }
    private static void searchIssued(User user){
        User userr = user;
        try {
            String query = "SELECT * FROM issuedBooks WHERE uid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userr.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                searchBook(resultSet.getString("bid"));
            }System.out.println("----------------------------------------------------------\n");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void issuedByMe(User user){
        searchIssued(user);
    }

    //SQL QUERIES
    private static void connectDb(){
        try {
            createUserDb();
            createBookDb();
            createIssuedBookDb(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUserDb() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " +
                "users(" +
                "uId VARCHAR(20) PRIMARY KEY," +
                "uName VARCHAR(200) not null," +
                "uEmail VARCHAR(50)," +
                "uPassword VARCHAR(50),"+
                "uRole VARCHAR(20)"+
                ")";

        //create a statement
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
    private static void createBookDb()throws SQLException{
        String query = "CREATE TABLE IF NOT EXISTS " +
                "books(" +
                "bId VARCHAR(20) PRIMARY KEY," +
                "bName VARCHAR(200) NOT NULL," +
                "bPrice INT,"+
                "bAuthor VARCHAR(20)"+
                ")";
        Statement statement = connection.createStatement();
        statement.execute(query);
    }
    private static void createIssuedBookDb(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+
                "issuedBooks("+
                "bId VARCHAR(50) PRIMARY KEY ,"+
                "FOREIGN KEY (bId) REFERENCES books(bId),"+
                "uId VARCHAR(50),"+
                "FOREIGN KEY (uId) REFERENCES users(uId)" +
                ")";
        Statement statement = connection.createStatement();
        statement.execute(query);
    }
    public static void initiateDb(){
        connectDb();
    }
    //--------INSERT DATA IN SQL DATABASE
    public static void insertUser(User user){
        insertUserToDB(user);
    }
    private static void insertUserToDB(User user) {
        try {
            if(!checkNullTables("users")){
                String query = "INSERT INTO users(uId,uName,uEmail,uPassword,uRole) values(?,?,?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(query);
                String uId = user.getUserId();
                String uName = user.getUsername();
                String uPassword = user.getPassword();
                String uEmail = user.getEmailId();
                String uRole = user.getRole().toString();

                pstmt.setString(1,uId);
                pstmt.setString(2,uName);
                pstmt.setString(3,uEmail);
                pstmt.setString(4,uPassword);
                pstmt.setString(5,uRole);

                pstmt.executeUpdate();
                System.out.println("\n!!!!! User Succesfully added !!!\n"
                        +"\nUser Id : "+uId
                        +"\nUser Name : "+uName
                        +"\nUser Email : " +uEmail
                        +"\nUser Type : "+uRole
                );
            }
            else {
                System.out.println("!!!!!!!!! Table Not Found !!!!!!!!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User checkLogin(String userName, String userPassword){

        return validateCredentials(userName,userPassword);
    }
    private static User validateCredentials(String userName, String userPassword){

        User user = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE uName LIKE '%"+userName+"%'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){

                String name = resultSet.getString("uName");
                String password = resultSet.getString("uPassword");
                String id = resultSet.getString("uid");
                String email = resultSet.getString("uEmail");
                UserType role = UserType.valueOf(resultSet.getString("uRole"));

                if ((name).equals(userName) && (password).equals(
                        Base64.getEncoder().encodeToString(userPassword.getBytes()))){

                    System.out.println(" \n!!!! User Found !!!!!!!!\n");
                    user = new User(name,password,role,email);
                    user.setUserId(id);
                    return user;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User delUserFromDb() throws SQLException {
        User user = null;
        Scanner scanner = new Scanner(System.in);
        try {

            if (Boolean.FALSE.equals(checkNullTables("users"))){

                System.out.print("ENTER ID OR NAME YOU WANT TO DELETE : ");
                String userId = scanner.next();

                getUserDetails(userId);

                System.out.println("Enter User ID you want to delete from above : ");
                String id = scanner.next();

                user = getUserDetails(id);

                System.out.println("Do you Want to Delete This User ? (y/n)");
                System.out.println(id);
                int ch = scanner.next().charAt(0);
                if (ch == 'y' || ch == 'Y'){

                    if (user.getUserId() != null) {
                        String query = "DELETE FROM users WHERE uid = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,id);
                        int rowDeleted = preparedStatement.executeUpdate();

                        if (rowDeleted != 0){
                            System.out.println("!!!! User Deleted !!!!");
                        }
                        else {
                            System.out.println("!!!! User Not Found !!!!");
                        }
                    }

                }else if (ch == 'n' || ch == 'N'){
                    librarianService.menu();
                }
            }
            else {
                System.out.println("!!!!!!!!! Table Not Found !!!!!!!!!!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public static User getUserDetails(String data) throws SQLException {
        User user = null;
        try{
            String query = "SELECT * FROM users WHERE uid = ? OR uName = ? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,data);
            pstmt.setString(2,data);
            ResultSet resultSet = pstmt.executeQuery();


            user = printDynamicUser(resultSet);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public static void printAllUsers() {
        String query = "SELECT * from users";
        printStaticUser(query);
    }
    private static void printStaticUser(String query){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                String id = resultSet.getString("uid");
                String name = resultSet.getString("uName");
                String email = resultSet.getString("uEmail");
                UserType role = UserType.valueOf(resultSet.getString("uRole"));

                System.out.println("____________________________________________________________________________________\n"+"| "
                        +id+" | "
                        +name+" | "
                        +email+" | "
                        +role+" |"
                );
            }
            System.out.println("____________________________________________________________________________________\n");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static User printDynamicUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        while (resultSet.next()){
            user.setUserId(resultSet.getString("uid"));
            user.setUsername(resultSet.getString("uName"));
            user.setEmailId(resultSet.getString("uEmail"));
            user.setRole(UserType.valueOf(resultSet.getString("uRole")));

            System.out.println("____________________________________________________________________________________\n"+"| "
                    +resultSet.getString("uid")+" | "
                    +resultSet.getString("uName")+" | "
                    +resultSet.getString("uEmail")+" | "
                    +resultSet.getString("uRole")+" |"
            );
        }
        System.out.println("____________________________________________________________________________________\n");
        return user;
    }
    public static void printIssuedBooks(){

        try{
            Statement statement = connection.createStatement();
//            String q = "SELECT COUNT(*) FROM issuedBooks";
//            ResultSet rSet = statement.executeQuery(q);
            if (Boolean.FALSE.equals(checkNullTables("issuedBooks"))){
                String query = "SELECT * FROM issuedBooks";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    String bId = resultSet.getString("bId");
                    String uId = resultSet.getString("uId");
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("| Book Id : "+bId+" | User Id : "+uId+" |");
                } System.out.println("-------------------------------------------------------------------------\n");
                return;
            }else {
                System.out.println("\n!!!!!!!!!! No Issued Books !!!!!!!");
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
    public static Boolean checkNullTables(String tableName){
        try {
            String query = "SELECT COUNT(*) FROM "+tableName;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int rowCount = resultSet.getInt(1);
                return rowCount == 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Book searchBook(String bId){
        int bNo = 0;
        try {
            if(Boolean.FALSE.equals(checkNullTables("books"))){
                String query = "SELECT * FROM books WHERE bId = ? OR bName = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1,bId);
                pstmt.setString(2,bId);
                ResultSet resultSet = pstmt.executeQuery();
                return printDynamicBook(resultSet);
            }else {
                System.out.println("!!!!!!!! Books Not Avaliable !!!!!!!!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private static Book printDynamicBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        int bookNo = 0;
        while (resultSet.next()){
            bookNo++;
            book.setBookId(resultSet.getString("bId"));
            book.setBookName(resultSet.getString("bName"));
            book.setAuthor(resultSet.getString("bAuthor"));
            book.setBookPrice(resultSet.getInt("bPrice"));
            System.out.println("____________________________________________________________________________\n| "+
                    resultSet.getString("bId")+" | "
                    +resultSet.getString("bName")+" | "
                    +resultSet.getString("bAuthor")+" | "
                    +resultSet.getInt("bPrice")+" |"
            );
        }
        System.out.println("____________________________________________________________________________\n");
        if (bookNo == 0){
            System.out.println("!!!!!!! Book Not Found !!!!!!!!!\n");
            System.out.println("____________________________________________________________________________\n");
            return book;
        }else {
            System.out.println("------- "+bookNo+" Books Found -------\n");
            System.out.println("____________________________________________________________________________\n");
            return book;
        }
    }
    public static void printStaticBooks(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int bookNo = 0;
        while (resultSet.next()){
            bookNo++;
            System.out.println("____________________________________________________________________________\n| "+
                    resultSet.getString("bId")+" | "
                    +resultSet.getString("bName")+" | "
                    +resultSet.getString("bAuthor")+" | "
                    +resultSet.getInt("bPrice")+" |"
            );
        }
        System.out.println("____________________________________________________________________________\n");
        if (bookNo == 0){
            System.out.println("!!!!!!! Book Not Found !!!!!!!!!\n");
            System.out.println("____________________________________________________________________________\n");
        }else {
            System.out.println("------- "+bookNo+" Books Found -------\n");
            System.out.println("____________________________________________________________________________\n");
        }
    }
    public static void printAllBooks(){
        try{
            if (Boolean.FALSE.equals(checkNullTables("books"))){
                String query = "SELECT * FROM books";
                printStaticBooks(query);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void addBook(Book book){
        try{
            String query = "INSERT INTO books(bId,bName,bAuthor,bPrice) values(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,book.getBookId());
            pstmt.setString(2,book.getBookName());
            pstmt.setString(3,book.getAuthor());
            pstmt.setInt(4,(int) book.getBookPrice());
            if (pstmt.executeUpdate() == 1){
                System.out.println("-------------- BOOK ADDED SUCCESFULLY --------------");
                System.out.println("____________________________________________________________________________\n| "+
                        book.getBookId()+" | "
                        +book.getBookName()+" | "
                        +book.getAuthor()+" | "
                        +book.getBookPrice()+" |"
                );

                System.out.println("____________________________________________________________________________\n");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void issueBook(String userId, String bookId){
        String c ;
        try {
            searchBook(bookId);
            getUserDetails(userId);
            System.out.println("========================================================");
            System.out.println("Do you Want To Proceed : (y/n)");
            c = InputReader.getString();
            if(c.charAt(0) == 'y' || c.charAt(0) == 'Y'){

                String query = "INSERT INTO issuedBooks(bId,uId) values(?,?)";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1,bookId);
                pstmt.setString(2,userId);
                if (pstmt.executeUpdate() > 0){
                    System.out.println("Book Id : "+bookId
                            +"\nUser Id : "+userId
                            +"\n!!!!!!!! BOOK ISSUED SUCCESFULLY !!!!!!");
                }else {
                    System.out.println("Book Id : "+bookId
                            +"\nUser Id : "+userId
                            +"\n!!!!!!!! BOOK NOT ISSUED !!!!!!");
                }
            } else if (c.charAt(0) == 'n' || c.charAt(0) == 'N') {
                System.out.println("YOU CHOOSE N ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static Book getBookDetails(String data) throws SQLException {
        Book book = null;
        try{
            String query = "SELECT * FROM books WHERE bId = ? OR bName = ? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,data);
            pstmt.setString(2,data);
            ResultSet resultSet = pstmt.executeQuery();

            book = printDynamicBook(resultSet);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return book;
    }


}
