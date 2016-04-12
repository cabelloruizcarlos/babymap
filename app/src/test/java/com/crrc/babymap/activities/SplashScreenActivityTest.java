package com.crrc.babymap.activities;

import android.app.Activity;

import com.crrc.babymap.app.activities.SplashScreenActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static android.test.MoreAsserts.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class SplashScreenActivityTest {

  @Mock
  Activity mMockActivity;

  @Test
  public void getActivitytest(){

    //  create mock
    SplashScreenActivity test = Mockito.mock(SplashScreenActivity.class);

    // define return value for method getUniqueId()
    when(test.getActivity()).thenReturn(mMockActivity);

    // use mock in test....
    assertTrue(test.getActivity().equals(mMockActivity));
  }

/*
  private static final String FAKE_STRING = "HELLO WORLD";

  @Mock
  Context mMockContext;

	@Test
	public void readStringFromContext_LocalizedString() {
		// Given a mocked Context injected into the object under test...
		when(mMockContext.getString(R.string.hello_word))
				.thenReturn(FAKE_STRING);
		ClassUnderTest myObjectUnderTest = new ClassUnderTest(mMockContext);

		// ...when the string is returned from the object under test...
		String result = myObjectUnderTest.getHelloWorldString();

		// ...then the result should be the expected one.
		assertThat(result, is(FAKE_STRING));
	}


	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}*/
}