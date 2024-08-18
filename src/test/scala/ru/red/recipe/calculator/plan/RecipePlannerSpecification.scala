package ru.red.recipe.calculator.plan

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import domain._

trait SimpleDomain {
  case class SimpleMachine(name: String) extends Machine

  case class SimpleRecipe(
      madeIn: Machine,
      components: Seq[Component],
      output: Seq[Component]
  ) extends Recipe

  case class SimpleItem(name: String) extends Item

  val furnace: Machine = SimpleMachine("Furnace")
  val shapedCrafting: Machine = SimpleMachine("Shaped Crafting")
  val ironIngot: Item = SimpleItem("Iron Ingot")
  val ironOre: Item = SimpleItem("Iron Ore")
  val ironDust: Item = SimpleItem("Iron Dust")
  val stick: Item = SimpleItem("Stick")
  val ironPickaxe: Item = SimpleItem("Iron Pickaxe")

  val ironIngotOreRecipe: Recipe =
    SimpleRecipe(
      furnace,
      Seq(Component(ironOre, 1)),
      Seq(Component(ironIngot, 1), Component(ironDust, 2))
    )

  val ironIngotDustRecipe: Recipe =
    SimpleRecipe(
      furnace,
      Seq(Component(ironDust, 4)),
      Seq(Component(ironIngot, 1))
    )

  val ironPickaxeRecipe: Recipe =
    SimpleRecipe(
      furnace,
      Seq(Component(ironIngot, 3), Component(stick, 2)),
      Seq(Component(ironPickaxe, 1))
    )

  val recipes: List[Recipe] =
    List(ironIngotOreRecipe, ironIngotDustRecipe, ironPickaxeRecipe)
}

abstract class RecipePlannerSpecification
    extends FixtureAnyFlatSpec
    with SimpleDomain {

  override protected final type FixtureParam = RecipePlanner

  "findOptimal" should "plan None if nothing is provided" in { planner =>
    planner.findOptimal(Seq.empty, Seq.empty) shouldBe empty
  }

  it should "plan None if required is not provided" in { planner =>
    planner.findOptimal(recipes, Seq.empty) shouldBe empty
  }

  it should "plan required if recipes are not provided" in { planner =>
    planner.findOptimal(
      Seq.empty,
      Seq(Component(ironPickaxe, 1))
    ) shouldBe Some(
      RecipePlan(
        output = Seq(Component(ironPickaxe, 1)),
        graph = Craft(
          recipe = Provided(Seq(Component(ironPickaxe, 1))),
          times = 1,
          next = Some(Leaf(Seq(Component(ironPickaxe, 1))))
        )
      )
    )
  }

  it should "prefer variant with no byproducts" in { planner =>
    planner.findOptimal(
      recipes,
      Seq(Component(ironIngot, 1))
    ) shouldBe Some(
      RecipePlan(
        output = Seq(Component(ironIngot, 1)),
        graph = Craft(
          recipe = Provided(Seq(Component(ironDust, 4))),
          times = 1,
          next = Some(Leaf(Seq(Component(ironIngot, 1))))
        )
      )
    )
  }

  it should "change recipe midway" in { planner =>
    planner.findOptimal(
      recipes,
      Seq(Component(ironPickaxe, 1))
    ) shouldBe Some(
      RecipePlan(
        output = Seq(Component(ironPickaxe, 1)),
        graph = Craft(
          recipe = Provided(Seq(Component(ironOre, 2))),
          times = 1,
          next = Some(
            Craft(
              recipe = ironIngotOreRecipe,
              times = 2,
              next = Some(
                Craft(
                  recipe = ironIngotDustRecipe,
                  times = 1,
                  next = Some(
                    Craft(
                      recipe = Provided(Seq(Component(stick, 3))),
                      times = 1,
                      next = Some(
                        Craft(
                          recipe = ironPickaxeRecipe,
                          times = 1,
                          next = Some(Leaf(Seq(Component(ironPickaxe, 1))))
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    )
  }

}
