__author__ = 'Viral_Shah'



from b.WordFrequencyCounter import compute_word_frequencies
import itertools


def compute_two_gram_frequencies(words: list):
    """
    :param words: takes in a list of strings
    :return: returns a list of frequency objects

    Uses Python's itertools to take pairwise pairs of strings and count their frequencies and return them using WordFrequencyCounter
    """
    if not words:
        return []
    a, b = itertools.tee(words)
    next(b, None)
    pairs = zip(a, b)
    two_grams = [" ".join(p) for p in pairs]
    return compute_word_frequencies(two_grams)