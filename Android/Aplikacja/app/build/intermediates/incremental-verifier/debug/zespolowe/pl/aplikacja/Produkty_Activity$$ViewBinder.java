// Generated code from Butter Knife. Do not modify!
package zespolowe.pl.aplikacja;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Produkty_Activity$$ViewBinder<T extends zespolowe.pl.aplikacja.Produkty_Activity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493026, "field 'podziel_koszty_button'");
    target.podziel_koszty_button = finder.castView(view, 2131493026, "field 'podziel_koszty_button'");
  }

  @Override public void unbind(T target) {
    target.podziel_koszty_button = null;
  }
}