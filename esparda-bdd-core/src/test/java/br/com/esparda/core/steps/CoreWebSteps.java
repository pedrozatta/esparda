package br.com.esparda.core.steps;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CoreWebSteps {

	@Given("a $width by $height game")
	@Aliases(values = { "a new game: $width by $height" })
	public void theGameIsRunning(int width, int height) {
		System.out.println();
	}

	@When("I toggle the cell at ($column, $row)")
	public void iToggleTheCellAt(int column, int row) {
		System.out.println();
	}

	@Then("the grid should look like $grid")
	@Aliases(values = { "the grid should be $grid" })
	public void theGridShouldLookLike(String grid) {
		System.out.println();
	}

}