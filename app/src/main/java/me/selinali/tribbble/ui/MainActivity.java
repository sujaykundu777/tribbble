package me.selinali.tribbble.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.selinali.tribbble.R;
import me.selinali.tribbble.ui.BinaryBar.Item;
import me.selinali.tribbble.ui.archive.ArchiveFragment;
import me.selinali.tribbble.ui.deck.DeckFragment;
import me.selinali.tribbble.utils.ViewUtils;

public class MainActivity extends AppCompatActivity {

  private static final String TAG_DECK_FRAGMENT = "TAG_DECK_FRAGMENT";
  private static final String TAG_ARCHIVE_FRAGMENT = "TAG_ARCHIVE_FRAGMENT";

  @BindView(R.id.container) View mContainer;
  @BindView(R.id.binary_bar) BinaryBar mBinaryBar;

  private final Map<String, Fragment> mFragments = new HashMap<>(2);
  private final Animation mAnimation = new AlphaAnimation(0, 1);
  private final Item mLeftItem = new Item(R.string.deck, R.drawable.ic_deck,
      v -> swapFragment(TAG_DECK_FRAGMENT));
  private final Item mRightItem = new Item(R.string.archive, R.drawable.ic_archive,
      v -> swapFragment(TAG_ARCHIVE_FRAGMENT));

  {
    mAnimation.setDuration(200);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setupMargins();

    mFragments.put(TAG_DECK_FRAGMENT, DeckFragment.newInstance());
    mFragments.put(TAG_ARCHIVE_FRAGMENT, ArchiveFragment.newInstance());

    swapFragment(TAG_DECK_FRAGMENT);
    mBinaryBar.addItems(mLeftItem, mRightItem);
  }

  private void swapFragment(String tag) {
    FragmentManager manager = getSupportFragmentManager();
    Fragment currentFragment = manager.findFragmentByTag(tag);
    if (currentFragment != null && currentFragment.isVisible()) {
      return;
    }

    mContainer.setAnimation(mAnimation);
    mAnimation.start();

    String otherTag = tag.equals(TAG_DECK_FRAGMENT) ? TAG_ARCHIVE_FRAGMENT : TAG_DECK_FRAGMENT;
    if (manager.findFragmentByTag(tag) != null) {
      manager.beginTransaction()
          .show(manager.findFragmentByTag(tag))
          .commit();
    } else {
      manager.beginTransaction()
          .add(R.id.container, mFragments.get(tag), tag)
          .commit();
    }
    if (manager.findFragmentByTag(otherTag) != null) {
      manager.beginTransaction()
          .hide(manager.findFragmentByTag(otherTag))
          .commit();
    }
  }

  private void setupMargins() {
    int navigationBarHeight = ViewUtils.getNavigationBarHeight();
    ViewUtils.setBottomMargin(mBinaryBar, navigationBarHeight + ViewUtils.dpToPx(16));
  }

  public void showBottomBar(boolean show) {
    ViewUtils.fadeView(mBinaryBar, show, 150);
  }
}