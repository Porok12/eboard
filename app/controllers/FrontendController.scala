package controllers

import play.api.Configuration
import play.api.http.HttpErrorHandler
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject

class FrontendController @Inject()(
                                    assets: Assets,
                                    errorHandler: HttpErrorHandler,
                                    config: Configuration,
                                    cc: ControllerComponents)
  extends AbstractController(cc) {

  def index: Action[AnyContent] = assets.at("index.html")

  def assertOrDefault(resource: String): Action[AnyContent] = {
    if (resource.startsWith(config.get[String]("apiPrefix"))) {
      Action.async(r => errorHandler.onClientError(r, NOT_FOUND, "Not found"))
    } else {
      if (resource.contains(".")) assets.at(resource) else index
    }
  }
}
