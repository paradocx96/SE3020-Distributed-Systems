Question 1: What does the count increment feature say about the Server object’s instantiation method?
Is it Singleton, Per client or Per call instantiation?

Answer: 
It is singleton.

Question 2: Briefly explain how can you make this a Per client or Per call instantiation?
(Hint: you can have multiple server objects and the Math Server object can have associations with other objects).

Answer: 
We must create separate objects for each client. Mainly happened is create separate objects for each client and each call.

For per client, we have to create separate MathClient class and server objects to call methods inside MathServer class.

For per call, we have to create separate server objects inside MathClient class for call methods inside MathServer class.
