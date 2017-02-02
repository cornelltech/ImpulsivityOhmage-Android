package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.skin.ActionItem;
import org.researchstack.skin.UiManager;
import org.researchstack.skin.ui.LearnActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityUIManager extends UiManager {
    @Override
    public List<ActionItem> getMainActionBarItems() {
        List<ActionItem> navItems = new ArrayList<>();

                navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_learn)
                .setTitle(R.string.rss_learn)
                .setIcon(R.drawable.rss_ic_action_learn)
                .setClass(LearnActivity.class)
                .build());

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_settings)
                .setTitle(R.string.rss_settings)
                .setIcon(R.drawable.rss_ic_action_settings)
                .setClass(ImpulsivitySettingsActivity.class)
                .build());

        return navItems;
    }

    @Override
    public List<ActionItem> getMainTabBarItems() {
        List<ActionItem> navItems = new ArrayList<>();

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_activities)
                .setTitle(R.string.ctf_scheduled_activities)
                .setIcon(R.drawable.rss_ic_tab_activities)
                .setClass(ImpulsivityActivitiesFragment.class)
                .build());




        return navItems;
    }

    @Override
    public Step getInclusionCriteriaStep(Context context) {
        return null;
    }

    @Override
    public boolean isInclusionCriteriaValid(StepResult result) {
        return false;
    }
}
