package ca.friends.and.co.it.smartparking;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class espressoTest {
@Rule
public ActivityScenarioRule<LoginActivity> activity = new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

@Test
public void test_app_email(){
    onView(withId(R.id.etLoginEmail)).perform(click());
    onView(isAssignableFrom(EditText.class)).perform(typeText("bhandarirushi13@gmail.com"));

}

@Test
public void test_app_password(){
    onView(withId(R.id.etLoginPass)).perform(click());
    onView(isAssignableFrom(EditText.class)).perform(typeText("Rushi123"),pressKey(KeyEvent.KEYCODE_ENTER));
}
@Test
public void test_app_google(){
    onView(withId(R.id.googleID)).perform(click());

}
@Test
    public void test_app_register(){
    onView(withId(R.id.tvRegisterHere)).perform(click());
}
@Test
    public void test_enter_login_credential(){
    onView(withId(R.id.etLoginEmail)).perform(click());
    onView(isAssignableFrom(EditText.class)).perform(typeText("bhandarirushi13@gmail.com"));
    onView(withId(R.id.etLoginPass)).perform(click());
    onView(isAssignableFrom(EditText.class)).perform(typeText("Rushi123"),pressKey(KeyEvent.KEYCODE_ENTER));
    onView(withId(R.id.btnLogin)).perform(click());

}


}
