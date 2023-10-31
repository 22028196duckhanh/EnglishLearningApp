import sys
sys.path.insert(0, 'pyttsx3')
sys.path.insert(0, 'speech_recognition')

import speech_recognition as sr
import pyttsx3

go = 1

# Initialize the recognizer
r = sr.Recognizer()

# Function to convert text to speech
def SpeakText(command):
    # Initialize the engine
    engine = pyttsx3.init()
    engine.say(command)
    engine.runAndWait()

def listen_and_recognize():
    recognized_text = ""  # Initialize recognized_text with a default value

    try:
        # Use the microphone as the source for input
        with sr.Microphone() as source:
            # Adjust the ambient noise threshold more quickly
            r.adjust_for_ambient_noise(source, duration=0.2)

            # Keep listening until 'u' is pressed
            # Listen for the user's input with a shorter timeout
            audio = r.listen(source, timeout=3,phrase_time_limit=2)  # Adjust the timeout as needed

            # Using Google to recognize audio
            recognized_text = r.recognize_google(audio)
            recognized_text = recognized_text.lower()
            print(recognized_text)

    except sr.RequestError as e:
        print("Could not request results; {0}".format(e))
    except sr.UnknownValueError:
        print("")

listen_and_recognize()