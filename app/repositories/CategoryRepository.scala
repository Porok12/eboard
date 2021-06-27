package repositories

import models.Category

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.compat.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.commands.WriteResult

@Singleton
class CategoryRepository @Inject()(
                                  implicit executionContext: ExecutionContext,
                                  reactiveMongoApi: ReactiveMongoApi
                                  ) {
  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection("category"))

  def findAll(limit: Int = 100): Future[Seq[Category]] = {
    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Category])
        .cursor[Category](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Category]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Category]] = {
    collection.flatMap(
      _.find(BSONDocument("_id" -> id), Option.empty[Category])
        .one[Category]
    )
  }

  def create(category: Category): (Future[WriteResult], Category) = {
    val categoryWithId = category.copy(_id = Some(BSONObjectID.generate()))
    (collection.flatMap(_.insert(ordered = false)
      .one(categoryWithId)), categoryWithId)
  }

  def delete(id: BSONObjectID): Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }
}
