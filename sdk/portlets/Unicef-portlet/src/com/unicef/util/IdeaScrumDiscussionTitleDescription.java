/**
 * 
 */
package com.unicef.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author minddeft007
 *
 */
public class IdeaScrumDiscussionTitleDescription {
	
	private static final Map<String, String> titleDescription;
	static {
		titleDescription = new HashMap<String, String>();
		
		// Understand
		titleDescription
		.put("Define the Problem",
				"Let's create a really clear Problem Statement. Please contribute your thoughts about the problem we're trying to solve here. The goal is for us to articulate the business goals clearly, which may affect features and product strategies downstream.");
		
		titleDescription
			.put("PAINstorming",
				"The key to a winning design is to focus on the customer pain points.  For this, we must do some PAINstorming. <br>PAIN = Persona + Activity + Insights + Needsmapping <br>You can watch the attached video to gain an understanding of this method. The goal is to gain a deep and intuitive understanding of the tacit needs of the true user.");

		titleDescription
				.put("Personas",
						"We need to define who the users are. Let's do this by using 'personas'. A user persona is a representation of the goals and behavior of a hypothesized group of users, so we can focus on a manageable cast of characters, instead of focusing on hundreds or even thousands of individuals."
								+ "<br><br>What are your ideas about what personas we should develop? Do you have a mental models (like an emotion map or a cognitive workload map) that we can use? What is the day in the life of this persona?");
		
		titleDescription
				.put("Ethnography",
						"We need to perform ethnographic design research to not only define how the product is designed, used, experienced, and most important… but to find the meaning of the product to the user."
								+ "<br>User observations and interviews represent the most important element in this design research we need to do. You can watch the attached video to gain an understanding of this method.");
		
		titleDescription
				.put("Stakeholder Interview Notes",
						"Just as an idea needs multi-visioning, our organization’s vision and strategy must be understood through a variety of perspectives. So let’s interview the stakeholders and collect their perspectives here. \n\nLet’s not forget to develop an unbiased understanding of technical constraints and constrictions, and concealed opportunities.");
		
		titleDescription
				.put("Competitive Landscape",
						"Let's discuss and review the competitive landscape. This is a good place to include photos and profiles of existing products to emulate.");
		
		titleDescription
				.put("Insights, Blindspots, Vision Quest",
						"SLet's collect and itemize the insights and takeaways in this thread for easy access.");
		
		titleDescription
				.put("Success Metrics",
						"Let's identify what success metrics to measure for this Ideascrum.");
		
		// Diverge
		titleDescription
		.put("Start by emptying your idea teacup",
				"On this thread, share your initial ideas and thoughts, just release them all to make space for the more creative ideas. It’s like the zen master who overfills the student’s teacup… you have to empty it to allow new thoughts to come in. So don’t worry if they’re not amazing ideas, or are too amazing to share so soon… it’s all good. Just release your current thinking until you don’t have anything else to say. Once your teacup is empty, we can begin.");
		
		titleDescription
		.put("Open thinking","How can we open it up, maybe do open systems thinking here?");
		
		titleDescription
		.put("Deepen our customer insight", "Let’s become the customers and think about it from their perspective. What does the user really want to do here?");
		
		titleDescription
		.put("Let’s map it!", "Let’s create a map of the situation. Let’s start with a SWOT analysis, and maybe we can do a force analysis, a mind map, or an emotion map?");
		
		titleDescription
		.put("Let’s get visual", "Let’s get visual and create Crazy 8’s doodles about this problem. It’s simple: you take a piece of paper and fold it into 8 parts. You set a timer for one minute. Each team member will sketch an idea in one minute’s time, and do that eight times in a row. Don’t worry if you can’t do eight. Just free your mind and doodle. If the group is feeling anxious about it, do some multi-visioning to kickstart the ideation process.");
		
		titleDescription
		.put("Expand the idea", "How do we “billionify” a half-baked idea? Many ideas may seem like small ideas, but in reality, they could be seeds of billion-dollar ideas, if we only knew how to enlarge, deepen and modify them. Or maybe turn it into a global idea?");
		
		titleDescription
		.put("Let’s de-constrain our thinking", "What if money, time, people, supplies are not issues at all?");
		
		titleDescription
		.put("Let’s hunt for blindspots", "What is known and unknown about our project? Once we map this out, can we detect a blindspot, either in the customer or in our thinking?");
		
		titleDescription
		.put("Let’s reframe the idea", "How do we flip around, invert, or reverse this idea? Turning an idea inside out may sound crazy, but it can lead to some brilliant thinking.");
		
		titleDescription
		.put("What is the eco-system here?", "What’s the eco-system surrounding our customer and solution space?");
		
		titleDescription
		.put("Steal and make better", "Okay, instead of looking at a competitor’s approach, let’s look at something someone is doing in a completely different arena, and then apply it to our market. How do we significantly improve it. Or maybe combine multiple approaches. So what is the “iPhone” for our industry? How do we “uber-ize” our product?");
		
		titleDescription
		.put("Unleashing our core creativity", "What would it take for us to become more passionate about this idea? Is there something inside of ourselves that is limiting our creativity here?");
		
		
		// CONVERGE
		titleDescription
		.put("CPAINstorming", "What problems are you solving? How acute is the pain? How much will they pay for solve it?");
		
		titleDescription
		.put("Solution", "Top three features of what you offer");
		
		titleDescription
		.put("Foundational", "Where are the blindspots? What are the limiting beliefs?");
		
		titleDescription
		.put("Unique value proposition", "What is it and why does it matter? Is it really unique or better? If not yet, how can you make it so? To figure this out, ask yourself what is the one thing you were put on this planet to do? What is your passion?");
		
		titleDescription
		.put("Unfair advantage", "Can you be copied? How sustainable is your advantage? Can you build a moat around your business?");
		
		titleDescription
		.put("Customers & channels", "Who is your target customer? What is your path to the customer? How can you reach 10x more leads? Who can you partner with?");
		
		titleDescription
		.put("Revenue streams", "What is your revenue model? What is your pricing and margin strategy? When can you start making money and when can you be profitable? What metrics do you need to focus on? What is your business \"swing thought\"?");
		
		titleDescription
		.put("Costs and hurdles", "What are the startup/launch, customer acquisition and distribution costs? Who are the people and what are the resources you need to make it happen? How can you get started right away to validate the model?");
		
		titleDescription
		.put("First 5 steps", "What are your first five bold steps to make it happen?");
		
		titleDescription
		.put("Find the spine", "So what is the throughline of our innovation story?");
		
		// Prototype
		titleDescription
		.put("Define the MVP", "Clearly define and agree on the feature set of a minimally viable prototype.");
		
		titleDescription
		.put("User testing script", "Create a user testing script based on the tasks that needs to be tested and the questions that need to be answered.");
		
		titleDescription
		.put("Find a tool", "Look around for a prototyping tool suitable for our needs: InVision, UXPin, PowerPoint, or simple HTML/CSS provide acceptable prototyping media.");
		
		titleDescription
		.put("Timeboxing", "Figure out the acceptable time and cost constraints.");
		
		titleDescription
		.put("Goals", "What are the learning goals for the prototype?");
		
		
		// VALIDATION
		titleDescription
		.put("Interview findings", "Let’s collate and note the findings from our prototype demos. What did we expect and validate? What didn’t we expect and learned? What blindspot did it reveal? What do we need to test for and validate next time?");
		
		titleDescription
		.put("Pain points", "Copy over the top pain points from the Sketchpad and discuss.");
		
		titleDescription
		.put("Solution points", "Copy over the top solution points from the Sketchpad and discuss.");
		
		titleDescription
		.put("The BIG Vision", "Explain the Unique Value Proposition and Your Unfair Advantage");
		
		titleDescription
		.put("How you get there", "Explain how you make money: Revenue Models, Channels, Pricing Models, Market Segmentation, etc");
		
		titleDescription
		.put("Happily ever after", "What the world looks like because of YOU and this product/project. Also, put in a twist at the end to make it memorable.");
		
		titleDescription
		.put("Meet cute", "The Inciting Incident. Explain the BUMP - the Big Urgent Market Problem");
		
		titleDescription
		.put("Everything else", "Put everything else here: Team Composition, Technical Vision, Cost Structures, Risk Analysis, Key Metrics, Intellectual Property Strategy, Unifying Models");
		
		titleDescription
		.put("The throughline", "Let’s get really clear on the spine of our story…");
		
		titleDescription
		.put("Remember", "What are the key take aways for the next iteration?");
		
		
		// INNOSPECTIVE
		titleDescription
		.put("Three words", "On this thread, express yourself by sharing three words to describe your experience of this sprint.");
		
		titleDescription
		.put("Happy about", "What worked in this sprint and how can we improve it?");
		
		titleDescription
		.put("Need to improve", "What didn’t work, and how can we fix it or lose it.");
		
		titleDescription
		.put("Ideas for improving this process", "How can we improve the process we’ve developed? Who should we ask to join the team for the next sprint? What perspectives should we add?");
		
		titleDescription
		.put("Ideas for improving Agile Innovation", "What ideas do we have for improving the core process of Agile Innovation to send to breakthrough@futurelabconsulting.com?");
		
	}

	public static String getDescriptionTitle(String title) {
		return titleDescription.get(title);
	}

}
