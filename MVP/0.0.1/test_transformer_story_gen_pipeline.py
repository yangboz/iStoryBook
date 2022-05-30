# Example of usage
from transformers import pipeline

story_gen = pipeline("text-generation", "lHScomcom/gpt2-fairytales")
# tory_gen = pipeline("text-generation", "Irina/Fairytale")
print(story_gen(""))

