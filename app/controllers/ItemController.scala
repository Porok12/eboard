package controllers

import models.Item
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, BaseController, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.bson.BSONObjectID

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import repositories.{CategoryItemRepository, CategoryRepository, ItemRepository}

import scala.util.{Failure, Success}

class ItemController @Inject() (implicit executionContext: ExecutionContext,
                                val controllerComponents: ControllerComponents,
                                val itemRepository: ItemRepository) extends BaseController {
  def findAll(): Action[AnyContent] = Action.async {
    itemRepository.findAll().map(
      items => Ok(Json.toJson(items))
    )
  }

  def findOne(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] => {
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(objectId) => itemRepository.findOne(objectId).map {
          todoItem => Ok(Json.toJson(todoItem))
        }
        case Failure(_) => Future.successful(BadRequest("Cannot parse the todo id"))
      }
    }
  }

  def create(): Action[JsValue] = Action.async(controllerComponents.parsers.json) {
    implicit request => {
      request.body.validate[Item].fold(
        _ => Future.successful(BadRequest("Cannot parse request body")),
        item => {
          val result = itemRepository.create(item)
          result._1.map {
            _ => Created(Json.toJson(result._2))
          }
        }
      )
    }
  }

  def update(id: String): Action[JsValue] = Action.async(controllerComponents.parsers.json) {
    implicit request => request.body.validate[Item].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      item => {
        val objectIdTryResult = BSONObjectID.parse(id)
        objectIdTryResult match {
          case Success(objectId) => itemRepository.update(objectId, item).map {
            _ => Ok(Json.toJson(id))
          }
          case Failure(_) =>Future.successful(BadRequest("Cannot parse the todo id"))
        }
      }
    )
  }

  def delete(id: String): Action[AnyContent] = Action.async {
    implicit  request => {
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(objectId) => itemRepository.delete(objectId).map {
          result => Ok(Json.toJson(id))
        }
        case Failure(_) => Future.successful(BadRequest("Cannot parse the todo id"))
      }
    }
  }
}
