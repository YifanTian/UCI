__author__ = 'Viral_Shah'

from b.WordFrequencyCounter import compute_word_frequencies


def compute_palindrome_frequencies(words: list):
    """
    :param words: takes in a list of strings
    :return: returns list of frequencies of palindromes in list of strings

    SEE ANALYSIS FOR MORE EXPLANATIONS

    we create:
    a list of indices of beginnings of words in a joined string of the list of words
    a list of indices of ending of words in a joined string of the list of words
    a joined string of the list of words

    we then iterate over the string using range and expand around each character to find palindromes and record them, we then convert to list of frequencies
    """

    # create an array of palindromes
    palindromes = []
    #check if the words list is empty
    if not words:
        return palindromes

    else:
        complete_words, begin_indices, end_indices = setup(words)
        total_length = len(complete_words)

        # set the constant of minimum length of palindromes found
        MIN_LEN = 3

        for i in range(total_length):

            begin = i
            end = i + 1
            # handling odd length strings by setting before pointer to current char
            while (check_indexes(begin, end, total_length) and (check_reverse_match(begin, end, complete_words))):
                # When we encounter a space, we must skip over it, so a palindrome _abba_ is not counted we just move pointer backwards or forwards
                if complete_words[begin] == " ":
                    begin-=1
                    continue
                if complete_words[end] == " ":
                    end+=1
                    continue
                pal = complete_words[begin : end + 1]
                if (is_valid_palindrome(pal) and (begin in begin_indices) and (end in end_indices)):
                    palindromes.append(pal)
                # expand outwards
                begin-=1
                end+=1

            # reinitialize begin and end for even length strings by setting pointer before and after current char
            begin = i - 1
            end = i + 1

            while (check_indexes(begin, end, total_length) and (check_reverse_match(begin, end, complete_words))):
                # When we encounter a space, we must skip over it, so a palindrome _abba_ is not counted we just move pointer backwards or forwards
                if complete_words[begin] == " ":
                    begin-=1
                    continue

                if complete_words[end] == " ":
                    end+=1
                    continue
                pal = complete_words[begin : end + 1]
                if (is_valid_palindrome(pal) and (begin in begin_indices) and (end in end_indices)):
                    palindromes.append(pal)
                # expand outwards
                begin-=1
                end+=1

        result = compute_word_frequencies(palindromes)
        return result


def check_indexes(begin, end, total_length):
    """
    :param begin: takes in the beginning index of the palindrome string
    :param end:  takes in the ending index of palindrome string
    :param total_length: takes in the total length of the string
    :return: returns true if the indexes are valid (between 0 and the total length)
    """
    return (begin >= 0) and (end < total_length)

def check_reverse_match(begin, end, complete_words):
    """

    :param begin: char index before current char
    :param end: character index after current char
    :param complete_words: string of all the words
    :return: returns true if the characters are equal or either one of them is a space
    """
    return complete_words[begin] == complete_words[end] or complete_words[begin] == " " or complete_words[end] == " "

def is_valid_palindrome(pal):
    """
    :param pal: takes in a string which is a palindrome
    Function checks if the palindrome is longer than 2 letters
    :return: returns boolean value if palindrome is longer than 2 letters
    """
    test = len(pal) > 2
    return test

def setup(words):
    """
    :param words: list of strings
    :return: returns list of indices of beginnings and ends and joined string of the words in the list of words
    """
    complete_words = ""
    begin_indices = []
    end_indices = []
    for word in words:
        b = len(complete_words)
        e = b + (len(word) - 1)
        begin_indices.append(b)
        end_indices.append(e)
        complete_words += word + " "
    return (complete_words.strip(), begin_indices, end_indices)