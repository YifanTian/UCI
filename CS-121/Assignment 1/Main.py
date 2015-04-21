__author__ = 'Viral_Shah'


from a.Utilities import Utilities
from b.WordFrequencyCounter import compute_word_frequencies
from c.TwoGramFrequencyCounter import compute_two_gram_frequencies
from d.PalindromeFrequencyCounter import compute_palindrome_frequencies

def main():
    file_path = ""
    instructions = """
    Enter 0 to set the input file
    Enter 1 to compute_word_frequencies
    Enter 2 to compute_2gram_frequencies
    Enter 3 to compute_palindrome_frequencies
    Enter 4 to exit

    """
    run = True
    while (run):
        response = input(instructions)

        if response == "0":
            file_path = input("Enter the path to the file: ")
            print("Tokenizing File")
            result = Utilities.tokenize_file(file_path)
            print("File Tokenized")
        elif response == "1":
            if file_path != "":
                print("Computing Word Frequencies")
                freqs = compute_word_frequencies(result)
                Utilities.print_frequencies(freqs)
                print("Done")
            else:
                print("No File Selected")
                continue
        elif response == "2":
            if file_path != "":
                freqs = compute_two_gram_frequencies(result)
                Utilities.print_frequencies(freqs)
                print("Done")
            else:
                print("No File Selected")
                continue
        elif response == "3":
            if file_path != "":
                freqs = compute_palindrome_frequencies(result)
                Utilities.print_frequencies(freqs)
                print("Done")
            else:
                print("No File Selected")
                continue
        elif response == "4":
            run = False
    return

if __name__ == '__main__':
    main()