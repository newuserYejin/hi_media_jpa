package com.ohgiraffers.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// JPA를 사용하지 않을떄의 문제 예시 상황
public class ProblemEXTests {
    
    // 1. test 코드 기반으로 jpa를 사용하지 않을 때
    
    // 테스트 전용 클래스 : @Test 어노테이션을 1개 이상 가지고 있는 클래스, 반환값이 없기 때문에 void 형으로 작성

    private Connection con;

    // Test 메소드 실행 전에 1번씩 동작
    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {
        
        // JDBC Connection을 만들기 위한 정보
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        // 동작 확인용 sout

        System.out.println("BeforeEach 동작");

        // DB 와 접속하기 위한 통로에 우리의 DB정보 저장
        Class.forName(driver);
        con = DriverManager.getConnection(url,user,password);
        // 자동 저장 끄기
        con.setAutoCommit(false);
    }
    
    
    @AfterEach
    void closeConnection() throws SQLException {
        System.out.println("connection 닫을꺼임(AfterEach)");
        con.rollback();
        con.close();
    }
    
    // JDBC를 직접 사용할때의 문제
    // 1. 데이터 변환, JDBC 중복 코드 작성, SQL문 작성
    // 2. SQL 의존적인 개발
    // 3. 패러다임 불일치 문제 발생
    // 4. 동일성 보장의 문제

    // 문제 확인
    // 1번

    @Test
    @DisplayName("직접 SQL 문을 작성해서 메뉴 조회 시 문제 확인")
    void testDirectSQL() throws SQLException {
        String query = "SELECT * FROM TBL_MENU";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        // DB 조회한 결과 담을 공간
        List<Menu> menuList = new ArrayList<>();

        while (rset.next()){
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("menu_code"));
            menu.setMenuName(rset.getString("menu_name"));
            menu.setMenuPrice(rset.getInt("menu_price"));
            menu.setCategoryCode(rset.getInt("category_code"));
            menu.setOrderableStatus(rset.getString("orderable_status"));

            menuList.add(menu);
        }

        // Assertions 클래스는 Test를 검증할 수 있는 메소드를 제공한다.

        Assertions.assertNotNull(menuList);
        menuList.forEach(menu -> System.out.println("menu = " + menu));

        rset.close();
        stmt.close();
    }
    
    // 2번
    // 고객(클라이언트) 측에서 요구사항이 변했을 때, JDBC 사용 시에는 DB DDL 수정 SQL 수정 Application 수정 필요

    // 3번
    
    // 4번
    @Test
    @DisplayName("조회한 두 개의 행을 담은 객체의 동일성 비교")
    void testEquals() throws SQLException {

        String query = "SELECT MENU_CODE,MENU_NAME FROM TBL_MENU WHERE MENU_CODE = 1";

        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;

        while (rset1.next()){
            menu1 = new Menu();

            menu1.setMenuCode(rset1.getInt("MENU_CODE"));
            menu1.setMenuName(rset1.getString("MENU_NAME"));
        }

        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;

        while (rset2.next()){
            menu2 = new Menu();

            menu2.setMenuCode(rset2.getInt("MENU_CODE"));
            menu2.setMenuName(rset2.getString("MENU_NAME"));
        }

        System.out.println("menu1 = " + menu1.hashCode());
        System.out.println("menu2 = " + menu2.hashCode());

        // 동일한 DB에서 같은 row에 해당하는 데이터를 꺼냈는데 각기 다른 객체에 담았을때 생기는 동일성 보장 실패 => 수정 시 다른곳에는 반영되지 않아 파악 불가
        Assertions.assertFalse(menu1 == menu2);

        rset1.close();
        stmt1.close();
        rset2.close();
        stmt2.close();
    }
}
