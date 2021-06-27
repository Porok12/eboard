package repositories

import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import models.{Category, CategoryItem, Item}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.akkastream.{AkkaStreamCursor, State, cursorProducer}
import reactivemongo.api.bson.{BSONArray, document}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.compat.{toDocumentReader, toDocumentWriter}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, BSONDocumentReader, BSONObjectID, Macros}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CategoryItemRepository @Inject()(
                                  implicit executionContext: ExecutionContext,
                                  reactiveMongoApi: ReactiveMongoApi,
                                  implicit val m: Materializer
                                  ) {
  def categoryCollection: Future[BSONCollection] =
    reactiveMongoApi.database.map(_.collection[BSONCollection]("category"))
  def itemCollection: Future[BSONCollection] =
    reactiveMongoApi.database.map(_.collection[BSONCollection]("item"))

//  implicit def categoryHandler = Macros.handler[Category]
//  implicit def itemHandler = Macros.handler[Item]
//  implicit val reader: BSONDocumentReader[BSONDocument] = Macros.reader[BSONDocument]

  def findAll(limit: Int = 100): Future[Seq[CategoryItem]] = {
    categoryCollection.map(c => {
      import c.AggregationFramework._

      val let = document("items" -> "$items")
      val pipeline = List(
        Match(
          document("$expr" -> document(
            document("$in" -> BSONArray(
                "$_id", "$$items"
              ))
            )
          )
        )
      )

      val lookup = LookupPipeline(
        "item",
        let,
        pipeline,
        "items")

      c.aggregatorContext[CategoryItem](List(lookup), comment=Some(""))
    }).flatMap(_.prepared[Cursor.WithOps].cursor
      .collect[Seq](limit, Cursor.FailOnError[Seq[CategoryItem]]()))

//    categoryCollection.map(c => {
//      import c.AggregationFramework._
//      c.aggregatorContext[CategoryItem](List(), comment=Some(""))
//    }).flatMap(_.prepared[AkkaStreamCursor.WithOps]
//      .cursor
//      .documentSource()
//      .runWith(Sink.seq[CategoryItem]))
  }

  def findOne(id: BSONObjectID): Future[Option[CategoryItem]] = {
    categoryCollection.map(c => {
      import c.AggregationFramework._

      val let = document("items" -> "$items")
      val pipeline = List(
        Match(
          document("$expr" -> document(
            document("$in" -> BSONArray(
              "$_id", "$$items"
            ))
          )
          )
        )
      )

      val lookup = LookupPipeline(
        "item",
        let,
        pipeline,
        "items")

      val idMatch = Match(document("_id" -> id.stringify))

      c.aggregatorContext[CategoryItem](List( idMatch, lookup), comment=Some(""))
    }).flatMap(_.prepared[Cursor.WithOps].cursor.headOption)
  }

  def create(categoryId: BSONObjectID, item: Item): (Future[WriteResult], Item) = {
    val itemWithId = item.copy(_id = Some(BSONObjectID.generate()))

    (itemCollection.flatMap(_.insert(ordered = false)
        .one(itemWithId)
      .andThen( _ => {
        categoryCollection.flatMap(
          _.findAndUpdate(
            BSONDocument("_id" -> categoryId),
            BSONDocument("$push" -> BSONDocument("items" -> itemWithId._id))
          )
        )
      })
    ), itemWithId)
  }
}