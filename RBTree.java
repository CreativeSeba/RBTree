package pack;

public class RBTree{
    Node root;
    public void add(int key, int value) {
        Node currentNode = root;
        Node parentNode = null;
        Node grandparentNode;
        Node uncleNode = null;
        Node childNode;

        if (root == null) {
            root = new Node(key, value);
            root.color = Node.Color.BLACK;
            return;
        }

        while (currentNode != null) {
            grandparentNode = parentNode;
            parentNode = currentNode;
            if (key == currentNode.key) {
                System.out.println("Key already exists");
                return;
            }
            if (grandparentNode != null) {
                if (parentNode == grandparentNode.left) {
                    uncleNode = grandparentNode.right;
                } else {
                    uncleNode = grandparentNode.left;
                }
            }

            // Sprawdzenie czy właściwości drzewa są spełnione
            if (grandparentNode != null && uncleNode != null && grandparentNode == root && uncleNode.color == Node.Color.RED) {
                uncleNode.color = Node.Color.BLACK;
                parentNode.color = Node.Color.BLACK;
                currentNode = grandparentNode;
            }

            if (key < currentNode.key) {
                childNode = currentNode.left;
                if (childNode == null) {
                    childNode = new Node(key, value);
                    parentNode.left = childNode;
                    childNode.parent = parentNode;
                    childNode.color = Node.Color.RED;
                    addRules(childNode);
                    return;
                }
            } else {
                childNode = currentNode.right;
                if (childNode == null) {
                    childNode = new Node(key, value);
                    parentNode.right = childNode;
                    childNode.parent = parentNode;
                    childNode.color = Node.Color.RED;
                    addRules(childNode);
                    return;
                }
            }
            currentNode = childNode;
        }
    }

    private void addRules(Node childNode) {
        while (childNode != root && childNode.parent.color == Node.Color.RED) {
            Node parentNode = childNode.parent;
            Node grandparentNode = parentNode.parent;

            //Rodzic jest lewym dzieckiem dziadka
            if (parentNode == grandparentNode.left) {
                Node uncleNode = grandparentNode.right;

                //Wujek jest czerwony
                if (uncleNode != null && uncleNode.color == Node.Color.RED) {
                    parentNode.color = Node.Color.BLACK;
                    uncleNode.color = Node.Color.BLACK;
                    grandparentNode.color = Node.Color.RED;
                    childNode = grandparentNode;
                } else {
                    //Wujek jest czarny albo null
                    if (childNode == parentNode.right) {
                        childNode = parentNode;
                        leftRotate(childNode);
                        parentNode = childNode.parent;
                    }
                    parentNode.color = Node.Color.BLACK;
                    grandparentNode.color = Node.Color.RED;
                    rightRotate(grandparentNode);
                }
            } else { //Rodzic jest prawym dzieckiem dziadka
                Node uncle = grandparentNode.left;

                //Wujek jest czerwony
                if (uncle != null && uncle.color == Node.Color.RED) {
                    parentNode.color = Node.Color.BLACK;
                    uncle.color = Node.Color.BLACK;
                    grandparentNode.color = Node.Color.RED;
                    childNode = grandparentNode;
                } else {
                    //Wujek jest czarny albo null
                    if (childNode == parentNode.left) {
                        childNode = parentNode;
                        rightRotate(childNode);
                        parentNode = childNode.parent;
                    }
                    parentNode.color = Node.Color.BLACK;
                    grandparentNode.color = Node.Color.RED;
                    leftRotate(grandparentNode);
                }
            }
        }
        //Korzeń zawsze jest czarny
        root.color = Node.Color.BLACK;
    }
    private void removeRules(Node childNode) {
        Node siblingNode;
        while (childNode != root && childNode.color == Node.Color.BLACK) {
            // Sprawdzenie, czy childNode jest lewym dzieckiem swojego rodzica
            if (childNode == childNode.parent.left) {
                siblingNode = childNode.parent.right;
                // Jeśli rodzeństwo jest czerwone
                if (siblingNode.color == Node.Color.RED) {
                    siblingNode.color = Node.Color.BLACK;
                    childNode.parent.color = Node.Color.RED;
                    leftRotate(childNode.parent); // Lewa rotacja
                    siblingNode = childNode.parent.right;
                }
                // Jeśli oba dzieci rodzeństwa są czarne
                if (siblingNode.left.color == Node.Color.BLACK && siblingNode.right.color == Node.Color.BLACK) {
                    siblingNode.color = Node.Color.RED;
                    childNode = childNode.parent;
                } else {
                    // Jeśli prawe dziecko rodzeństwa jest czarne
                    if (siblingNode.right.color == Node.Color.BLACK) {
                        siblingNode.left.color = Node.Color.BLACK;
                        siblingNode.color = Node.Color.RED;
                        rightRotate(siblingNode); // Prawa rotacja
                        siblingNode = childNode.parent.right;
                    }
                    siblingNode.color = childNode.parent.color;
                    childNode.parent.color = Node.Color.BLACK;
                    siblingNode.right.color = Node.Color.BLACK;
                    leftRotate(childNode.parent); // Lewa rotacja
                    childNode = root;
                }
            } else {
                // Analogiczne kroki dla przypadku, gdy childNode jest prawym dzieckiem
                siblingNode = childNode.parent.left;
                if (siblingNode.color == Node.Color.RED) {
                    siblingNode.color = Node.Color.BLACK;
                    childNode.parent.color = Node.Color.RED;
                    rightRotate(childNode.parent); // Prawa rotacja
                    siblingNode = childNode.parent.left;
                }
                if (siblingNode.right.color == Node.Color.BLACK && siblingNode.left.color == Node.Color.BLACK) {
                    siblingNode.color = Node.Color.RED;
                    childNode = childNode.parent;
                } else {
                    if (siblingNode.left.color == Node.Color.BLACK) {
                        siblingNode.right.color = Node.Color.BLACK;
                        siblingNode.color = Node.Color.RED;
                        leftRotate(siblingNode); // Lewa rotacja
                        siblingNode = childNode.parent.left;
                    }
                    siblingNode.color = childNode.parent.color;
                    childNode.parent.color = Node.Color.BLACK;
                    siblingNode.left.color = Node.Color.BLACK;
                    rightRotate(childNode.parent); // Prawa rotacja
                    childNode = root;
                }
            }
        }
        childNode.color = Node.Color.BLACK;
    }


    public void remove(int key) {
        Node currentNode = root;
        Node parentNode = null;
        Node grandparentNode;
        Node uncleNode = null;
        Node childNode = null;

        while (currentNode != null) {
            grandparentNode = parentNode;
            parentNode = currentNode;

            if (key == currentNode.key) {
                childNode = currentNode;
                break;
            }

            if (grandparentNode != null) {
                if (parentNode == grandparentNode.left) {
                    uncleNode = grandparentNode.right;
                } else {
                    uncleNode = grandparentNode.left;
                }
            }

            // Przejdź w lewo lub w prawo zaleznie od klucza
            if (key < currentNode.key) {
                childNode = currentNode.left;
            } else {
                childNode = currentNode.right;
            }

            currentNode = childNode;
        }

        // Jeśli węzeł do usunięcia nie jest znaleziony
        if (childNode == null) {
            System.out.println("Key not found");
            return;
        }

        Node temp = childNode;
        Node.Color originalColor = temp.color;
        Node x;

        // Kod zastąpywania
        if (childNode.left == null) {
            x = childNode.right;
            transplant(childNode, childNode.right);
        } else if (childNode.right == null) {
            x = childNode.left;
            transplant(childNode, childNode.left);
        } else {
            temp = minimum(childNode.right);
            originalColor = temp.color;
            x = temp.right;
            if (temp.parent != childNode) {
                transplant(temp, temp.right);
                temp.right = childNode.right;
                temp.right.parent = temp;
            }
            transplant(childNode, temp);
            temp.left = childNode.left;
            temp.left.parent = temp;
            temp.color = childNode.color;
        }

        //Napraw właściowści drzewa, jeśli oryginalny kolor jest czarny
        if (originalColor == Node.Color.BLACK) {
            removeRules(x);
        }
    }


    private void transplant(Node u, Node v) {
            if (u.parent == null) {
                root = v;
            } else if (u == u.parent.left) {
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
            if (v != null) {
                v.parent = u.parent;
            }
        }

        private Node minimum(Node node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }




    private void leftRotate(Node x) {
        if (x == null || x.right == null) {
            return;
        }
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        if (y == null || y.left == null) {
            return;
        }
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }

    public void showTree() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        System.out.println("\nRed-Black Tree:\n");
        displayTree(root, null);
        System.out.println("\n");
    }

    private void displayTree(Node node, Node parentNode) {
        if (node != null) {
            String position = "Root";
            if (parentNode != null) {
                if (node == parentNode.left) {
                    position = "L";
                } else if (node == parentNode.right) {
                    position = "R";
                }
            }
            switch (position) {
                case "Root":
                    System.out.println(redNode(node, position));
                    break;
                default:
                    System.out.println(redNode(node, position) + " | is child of: " + parentNode.key + " | grandfather: " + (parentNode.parent != null ? parentNode.parent.key : "null"));
            }

            displayTree(node.left, node);
            displayTree(node.right, node);
        }
    }

    private String redNode(Node node, String position) {
        String color = node.color == Node.Color.RED ? "\u001B[31m" : "\u001B[0m";
        return color + node + ": " + position + " " + (node.color == Node.Color.RED ? Node.Color.RED : Node.Color.BLACK) + "\u001B[0m";
    }

    public void get(int key) {
        int numOfOperations = 0;
        Node currentNode = root;
        Node parentNode = null;
        Node grandparentNode;
        String position = "Root";
        System.out.println("Route:\n");
        System.out.println(root + ": " + position + " " + root.color);
        while (currentNode != null) {
            if (key < currentNode.key) {
                position = "L";
                grandparentNode = parentNode;
                parentNode = currentNode;
                numOfOperations++;
                currentNode = currentNode.left;
                if (currentNode != null) {
                    System.out.println(redNode(currentNode, position) + " | is child of: " + parentNode + " | grandfather: " + grandparentNode);
                }
            } else if (key > currentNode.key) {
                position = "R";
                grandparentNode = parentNode;
                parentNode = currentNode;
                numOfOperations++;
                currentNode = currentNode.right;
                if (currentNode != null) {
                    System.out.println(redNode(currentNode, position) + " | is child of: " + parentNode + " | grandfather: " + grandparentNode);
                }
            } else {
                System.out.println("\nKey: " + currentNode.key + "\tValue: " + currentNode.value);
                System.out.println("Number of operations: " + numOfOperations);
                return;
            }
        }
        System.out.println("Key not found");
    }

    public void height() {
        int treeHeight = calculateHeight(root);
        System.out.println("\nHeight of the tree: " + treeHeight);
    }

    private int calculateHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = calculateHeight(node.left);
            int rightHeight = calculateHeight(node.right);
            int maxHeight = Math.max(leftHeight, rightHeight) + 1;
            return maxHeight;
        }
    }
}