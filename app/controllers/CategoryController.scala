package controllers

import models.{Category, Item}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, BaseController, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.bson.BSONObjectID

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import repositories.{CategoryItemRepository, CategoryRepository}

import scala.util.{Failure, Success}

class CategoryController @Inject()(
                                    implicit executionContext: ExecutionContext,
                                    val controllerComponents: ControllerComponents,
//                                    val reactiveMongoApi: ReactiveMongoApi,
                                    val categoryItemRepository: CategoryItemRepository,
                                    val categoryRepository: CategoryRepository,
                                  )
//  extends AbstractController(cc) with MongoController with ReactiveMongoComponents
  extends BaseController
{
//  implicit def ec: ExecutionContext = cc.executionContext

//  def collection: Future[BSONCollection] = database.map(_.collection[BSONCollection]("category"))

  def findAll(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      categoryItemRepository.findAll().map {
        categories => Ok(Json.toJson(categories))
      }
  }

  def findOne(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(objectId) => categoryRepository.findOne(objectId).map {
          categories => Ok(Json.toJson(categories))
        }
        case Failure(_) => Future.successful(BadRequest("Cannot parse the todo id"))
      }

  }

  def create(): Action[JsValue] = Action.async(controllerComponents.parsers.json) {
    implicit request => {
      request.body.validate[Category].fold(
        _ => Future.successful(BadRequest("Cannot parse request body")),
        category => {
          val result = categoryRepository.create(category)
            result._1.map {
              _ => Created(Json.toJson(result._2))
            }
        }
      )
    }
  }

  def delete(id: String): Action[AnyContent] = Action.async {
    implicit  request => {
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(objectId) => categoryRepository.delete(objectId).map {
          _ => Ok(Json.toJson(id))
        }
        case Failure(_) => Future.successful(BadRequest("Cannot parse the todo id"))
      }
    }
  }

  def createWithin(id: String): Action[JsValue] = Action.async(controllerComponents.parsers.json) {
    implicit request => {
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(categoryId) => request.body.validate[Item].fold(
          _ => Future.successful(BadRequest("Cannot parse request body")),
          item => {
            val result = categoryItemRepository.create(categoryId, item)
            result._1.map {
              _ => Created(Json.toJson(result._2))
            }
          }
        )
        case Failure(_) => Future.successful(BadRequest("Cannot parse the todo id"))
      }
    }
  }
}
