package views.html.tutor

import controllers.routes
import play.api.libs.json._

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._
import lila.common.LilaOpeningFamily
import lila.tutor.{ TutorMetric, TutorMetricOption, TutorPerfReport, TutorRatio, TutorReport }

object opening {

  def apply(
      fullReport: TutorReport,
      report: TutorPerfReport,
      opening: LilaOpeningFamily.AsColor,
      user: lila.user.User
  )(implicit ctx: Context) =
    views.html.base.layout(
      moreCss = frag(cssTag("tutor")),
      title = s"Lichess Tutor • ${report.perf.trans} • ${opening.color.name} • ${opening.family.name.value}"
    ) {
      main(cls := "page-menu tutor")(
        st.aside(cls := "page-menu__menu subnav")(
          a(href := routes.Tutor.user(user.username))("Tutor"),
          a(href := routes.Tutor.openings(user.username, report.perf.key))("Openings"),
          report.openings(opening.color).families map { family =>
            a(
              href := routes.Tutor
                .opening(user.username, report.perf.key, opening.color.name, family.family.key.value),
              cls := family.family.key.value.active(opening.family.key.value)
            )(family.family.name.value)
          }
        ),
        div(cls := "page-menu__content tutor__opening box box-pad")(
          h1(
            a(
              href     := routes.Tutor.openings(user.username, report.perf.key),
              dataIcon := "",
              cls      := "text"
            ),
            report.perf.trans,
            " ",
            opening.color.name,
            " ",
            opening.family.name
          )
        )
      )
    }
}
