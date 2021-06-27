package repositories

import models.Item

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.compat.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.commands.WriteResult

@Singleton
class ItemRepository @Inject()(
                              implicit executionContext: ExecutionContext,
                              reactiveMongoApi: ReactiveMongoApi
                              ) {
  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection("item"))

  def findAll(limit: Int = 100): Future[Seq[Item]] = {
    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Item])
        .cursor[Item](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Item]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Item]] = {
    collection.flatMap(
      _.find(BSONDocument("_id" -> id), Option.empty[Item])
        .one[Item]
    )
  }

  def create(item: Item): (Future[WriteResult], Item) = {
    val itemWithId = item.copy(_id = Some(BSONObjectID.generate()))
    (collection.flatMap(_.insert(ordered = false)
      .one(itemWithId)), itemWithId)
  }

  def update(id: BSONObjectID, item: Item): Future[WriteResult] = {
    collection.flatMap(
      _.update(ordered = false)
        .one(BSONDocument("_id" -> id), item.copy())
    )
  }

  def delete(id: BSONObjectID): Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }
}
