package ru.red.recipe.calculator.plan

object domain {
  trait Machine {
    val name: String
  }

  private case object NoMachine extends Machine {
    override val name: String = "No Machine"
  }

  trait Recipe {
    val madeIn: Machine
    val components: Seq[Component]
    val output: Seq[Component]
  }

  case class Provided(components: Seq[Component]) extends Recipe {
    override val madeIn: Machine = NoMachine
    override val output: Seq[Component] = components
  }

  trait Item {
    val name: String
  }

  case class Component(item: Item, count: Int)

  case class RecipePlan(
      output: Seq[Component],
      graph: RecipeGraph
  )

  sealed trait RecipeGraph

  case class Craft(recipe: Recipe, times: Int, next: Option[RecipeGraph])
      extends RecipeGraph

  case class Leaf(components: Seq[Component]) extends RecipeGraph
}
