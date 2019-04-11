package com.ss.newsapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ss.newsapp.model.NewsEntity;
import com.ss.newsapp.repository.NewsRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
	@Test
	public void useAppContext() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();

		assertEquals("com.ss.newsapp", appContext.getPackageName());
	}

	private NewsRepository repo;

	@Test
	public void insertTest() throws InterruptedException {
		Context appContext = InstrumentationRegistry.getTargetContext();
		repo = new NewsRepository(appContext);
		String author = "Times Of India";
		String title = "From doughnuts to Brexit, first-ever image of a black hole inspires hilarious memes - Scroll.in";
		String description = "The black hole in the image is at the centre of the Messier 87 galaxy, and is about 55 million light years away from Earth.";
		String url = "https://scroll.in/article/919689/from-doughnuts-to-brexit-first-ever-image-of-a-black-hole-inspires-hilarious-memes";

		NewsEntity entity = new NewsEntity();
		entity.setAuthor(author);
		entity.setTitle(title);
		entity.setDescription(description);
		entity.setUrl(url);
		repo.insert(entity);
		Thread.sleep(2500);
	}
}
