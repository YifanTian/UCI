__author__ = 'Viral_Shah'

from collections import Counter
from a.Frequency import Frequency

def compute_word_frequencies(words: list):
    """
    :param words: list of strings
    :return: uses the counter object to count the number of occurrences of each string and sorts them by frequency and
    alphabetically and returns them as Frequency objects in a list
    """
    if not words:
        return []
    count = [i for i in Counter(words).items()]
    result = [Frequency(i[0], i[1]) for i in sorted(count, key = lambda word: (-word[1], word[0]))]
    return result
